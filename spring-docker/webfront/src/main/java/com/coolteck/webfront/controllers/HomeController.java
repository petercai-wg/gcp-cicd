package com.coolteck.webfront.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coolteck.webfront.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("home", "View");
        return "index";
    }

    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("products", productService.listProduct());
        model.addAttribute("home", "List");
        return "index";
    }

    @GetMapping("health")
    @ResponseBody
    public String healthCheck() {
        return "OK";
    }

    @GetMapping("host")
    @ResponseBody
    public String hostname() {
        return productService.getHostname();
    }

}
