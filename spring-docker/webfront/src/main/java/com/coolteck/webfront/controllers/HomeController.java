package com.coolteck.webfront.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coolteck.webfront.services.ProductService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String index(Model model) {
        log.info("Rendering home page");
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("home", "View");
        return "index";
    }

    @RequestMapping("list")
    public String list(Model model) {
        log.info("Rendering list page");
        model.addAttribute("products", productService.listProduct());
        model.addAttribute("home", "List");
        return "index";
    }

    @GetMapping("health")
    @ResponseBody
    public String healthCheck() {
        return "OK \n";
    }

    @GetMapping("host")
    @ResponseBody
    public String hostname() {
        return productService.getHostname();
    }

}
