package com.academy.web;

import com.academy.domain.Inquiry;
import com.academy.repo.CourseRepository;
import com.academy.service.InquiryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    private final InquiryService inquiryService;
    private final CourseRepository courses;

    public ContactController(InquiryService inquiryService, CourseRepository courses) {
        this.inquiryService = inquiryService;
        this.courses = courses;
    }

    @GetMapping("/contact")
    public String form(@RequestParam(required = false) String course, Model model) {
        InquiryForm form = new InquiryForm();
        form.setCourse(course);
        model.addAttribute("form", form);
        model.addAttribute("courses", courses.findAllByOrderBySortOrder());
        return "contact";
    }

    @PostMapping("/contact")
    public String submit(@Valid @ModelAttribute("form") InquiryForm form, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("courses", courses.findAllByOrderBySortOrder());
            return "contact";
        }
        if (form.getWebsite() == null || form.getWebsite().isBlank()) {
            Inquiry inquiry = new Inquiry();
            inquiry.setName(form.getName().trim());
            inquiry.setEmail(form.getEmail().trim());
            inquiry.setPhone(form.getPhone());
            inquiry.setCourseInterest(form.getCourse());
            inquiry.setMessage(form.getMessage());
            inquiryService.submit(inquiry);
        }
        return "redirect:/contact?sent";
    }
}
