package com.TwitterClone.ProjectBackend.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ContentSecurityPolicyControllerAdvice {

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("nonce", request.getAttribute("nonce"));
        System.out.println(request.getAttribute("nonce"));
    }
}