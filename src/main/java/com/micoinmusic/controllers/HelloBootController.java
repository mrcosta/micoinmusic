package com.micoinmusic.controllers;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class HelloBootController {

    private static final Logger logger = getLogger(HelloBootController.class);

    @RequestMapping("/hi")
    public String sayHi() {
        logger.info("hello booter");
        return "hello";
    }
}
