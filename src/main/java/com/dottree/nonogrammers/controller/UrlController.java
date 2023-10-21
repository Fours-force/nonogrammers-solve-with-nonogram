package com.dottree.nonogrammers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class UrlController {
    @RequestMapping("/")
    public String home(){return "home";}

    @RequestMapping("/join")
    public String join(){return "join";}

    @RequestMapping("/login")
    public String login(){return "login";}

    @RequestMapping("/forgot-password")
    public String forgotPassword(){return "forgot-password";}

    @RequestMapping("/reset-password")
    public String resetPassword(){return "reset-password";}

    @RequestMapping("/write")
    public String writePost(){return "write";}

    @RequestMapping("/community")
    public String getCommunity(){return "community";}
}

