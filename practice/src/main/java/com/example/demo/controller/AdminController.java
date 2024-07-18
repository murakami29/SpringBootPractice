package com.example.demo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactUpdateForm;
import com.example.demo.form.SigninForm;
import com.example.demo.form.SignupForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/admin/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "admin/signup";
    }

    @PostMapping("/admin/signup")
    public String signup(@Validated @ModelAttribute("signupForm") SignupForm signupForm, BindingResult errorResult, HttpServletRequest request) {
        if (errorResult.hasErrors()) {
            return "admin/signup";
        }

        HttpSession session = request.getSession();
        session.setAttribute("signupForm", signupForm);
        return "redirect:/admin/signup/confirm";
    }

    @GetMapping("/admin/signup/confirm")
    public String confirm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SignupForm signupForm = (SignupForm) session.getAttribute("signupForm");
        model.addAttribute("signupForm", signupForm);
        return "admin/signupConfirmation";
    }

    @PostMapping("/admin/signup/register")
    public String register(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SignupForm signupForm = (SignupForm) session.getAttribute("signupForm");
        adminService.saveAdmin(signupForm);
        return "redirect:/admin/signup/complete";
    }

    @GetMapping("/admin/signup/complete")
    public String complete(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SignupForm signupForm = (SignupForm) session.getAttribute("signupForm");
        model.addAttribute("signupForm", signupForm);
        session.invalidate();
        return "admin/signupCompletion";
    }
    
    @GetMapping("/admin/signin")
    public String signin(Model model, HttpServletRequest request) {
        String error = request.getParameter("error");
        if ("true".equals(error)) {
            model.addAttribute("error", "メールアドレスまたはパスワードが正しくありません");
        }
        model.addAttribute("signinForm", new SigninForm());

        return "admin/signin";
    }
        
    @GetMapping("/admin/contacts")
    public String contact(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SigninForm signinForm = (SigninForm) session.getAttribute("signinForm");
        model.addAttribute("signinForm", signinForm);
        model.addAttribute("inquiries", contactService.getAllContacts());
        return "admin/contacts";
    }
    
    @GetMapping("/admin/contacts/:{id}")
    public String showContactDetails(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id).orElse(null);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "admin/contactDetail";
        } else {
            model.addAttribute("errorMessage", "お問い合わせが見つかりません");
            return "admin/error";
        }
    }
    @GetMapping("/admin/contacts/:{id}/edit")
    public String showEditContact(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id).orElse(null);
        if (contact != null) {
        	ContactUpdateForm contactUpdateForm = new ContactUpdateForm();
        	BeanUtils.copyProperties(contact, contactUpdateForm);
        	model.addAttribute("contact", contact);
        	model.addAttribute("contactUpdateForm", contactUpdateForm);
            return "admin/contactEdit";
        } else {
            model.addAttribute("errorMessage", "お問い合わせが見つかりません");
            return "admin/error";
        }
    }

    @PostMapping("/admin/contacts/update")
    public String updateContact(@Validated @ModelAttribute("contactUpdateForm") ContactUpdateForm contactUpdateForm, BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "admin/contactEdit";
        }
    	contactService.updateContact(contactUpdateForm);
        return "redirect:/admin/contacts";
    }
    
    @PostMapping("/admin/contacts/:{id}/delete")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContactById(id);
        return "redirect:/admin/contacts";
    }
}

