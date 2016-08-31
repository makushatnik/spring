package com.cdesign.spittr.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by RealXaker on 28.08.2016.
 */
@Controller
@RequestMapping({"/", "homepage"})
public class HomeController {

    @RequestMapping(method = GET)
    public String home() {
        return "home";
    }
}
