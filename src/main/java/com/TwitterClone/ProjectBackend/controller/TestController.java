package com.TwitterClone.ProjectBackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String testResturn() {
        //return a execution script if spring security configuration is disable
        return """
      {
    "id": 1,
    "name": "<script>alert('Hola >:)!')</script>"
    }
      """;
    }
}
