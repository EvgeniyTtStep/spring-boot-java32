package org.itstep.springbootjava32.controller;

import org.itstep.springbootjava32.dto.CaptchaResponseDTO;
import org.itstep.springbootjava32.model.User;
import org.itstep.springbootjava32.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;

@Controller
public class SecurityController {

    private UserService userService;


    private final RestTemplate restTemplate;

    public SecurityController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }





    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(@RequestParam("g-recaptcha-responce") String captionResponse, Model model) {
        restTemplate.postForObject("https://www.google.com/recaptcha/api/siteveryfy?secret=6LdM1S0qAAAAAEMOSOAcBXFMGzZgXF2Q_AICp87h&" + captionResponse, Collections.emptyList(), CaptchaResponseDTO.class);
        model.addAttribute("user", new User());
        return "registration";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        userService.saveAdminUser(user);
        return "redirect:/all-students";
    }



}
