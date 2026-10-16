package com.academy.service;

import com.academy.config.AppProperties;
import com.academy.domain.Inquiry;
import com.academy.repo.InquiryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class InquiryService {

    private static final Logger log = LoggerFactory.getLogger(InquiryService.class);

    private final InquiryRepository inquiries;
    private final ObjectProvider<JavaMailSender> mailSender;
    private final AppProperties props;

    public InquiryService(InquiryRepository inquiries, ObjectProvider<JavaMailSender> mailSender, AppProperties props) {
        this.inquiries = inquiries;
        this.mailSender = mailSender;
        this.props = props;
    }

    public void submit(Inquiry inquiry) {
        inquiries.save(inquiry);
        notifyAdmin(inquiry);
    }

    /** Optional e-mail to the admin; only when MAIL_HOST and NOTIFY_EMAIL are configured. */
    private void notifyAdmin(Inquiry inquiry) {
        JavaMailSender sender = mailSender.getIfAvailable();
        if (sender == null || !StringUtils.hasText(props.notifyEmail())) {
            return;
        }
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(props.notifyEmail());
            mail.setSubject("New inquiry: " + inquiry.getName());
            mail.setText("""
                    Name: %s
                    Email: %s
                    Phone: %s
                    Course: %s

                    %s

                    %s/admin/inquiries""".formatted(inquiry.getName(), inquiry.getEmail(), inquiry.getPhone(),
                    inquiry.getCourseInterest(), inquiry.getMessage(), props.baseUrl()));
            sender.send(mail);
        } catch (RuntimeException e) {
            log.warn("Could not send inquiry notification: {}", e.getMessage());
        }
    }
}
