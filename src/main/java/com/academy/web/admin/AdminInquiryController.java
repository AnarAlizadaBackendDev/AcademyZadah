package com.academy.web.admin;

import com.academy.domain.Inquiry;
import com.academy.domain.InquiryStatus;
import com.academy.repo.InquiryRepository;
import com.academy.web.NotFound;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/inquiries")
public class AdminInquiryController {

    private final InquiryRepository inquiries;

    public AdminInquiryController(InquiryRepository inquiries) {
        this.inquiries = inquiries;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("inquiries", inquiries.findAllByOrderByCreatedAtDesc());
        model.addAttribute("statuses", InquiryStatus.values());
        return "admin/inquiries";
    }

    @PostMapping("/{id}")
    @Transactional
    public String update(@PathVariable Long id, @RequestParam InquiryStatus status,
                         @RequestParam(required = false) String adminNote, RedirectAttributes redirect) {
        Inquiry inquiry = inquiries.findById(id).orElseThrow(NotFound::new);
        inquiry.setStatus(status);
        inquiry.setAdminNote(adminNote);
        redirect.addFlashAttribute("flash", "flash.saved");
        return "redirect:/admin/inquiries";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        inquiries.deleteById(id);
        redirect.addFlashAttribute("flash", "flash.deleted");
        return "redirect:/admin/inquiries";
    }
}
