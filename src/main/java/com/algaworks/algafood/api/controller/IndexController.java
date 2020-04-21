package com.algaworks.algafood.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }
}
