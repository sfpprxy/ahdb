package org.ahdb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ahdb")
@RestController
public class ViewController {
    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    ViewService viewService;

    @GetMapping(value = "/view")
    public String view() {
        viewService.view();
        return "OK";
    }

}
