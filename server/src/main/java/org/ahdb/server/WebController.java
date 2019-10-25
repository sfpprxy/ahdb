package org.ahdb.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.ValuableDataByAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebController {

    @GetMapping(value = "")
    public String index(String account) {
        log.debug("access index {}", account);
        return "index.html";
    }

    @GetMapping(value = "/item")
    public String item(String account, String item) {
        log.debug("access item {} {}", account, item);
        return "item.html";
    }


    @GetMapping(value = "/home")
    public String home(String account) {
        log.debug(account);
        return "redirect:/static/index.html";
    }

    @PostMapping(value = "/webtest")
    public String receive(@RequestBody ValuableDataByAccount valuableDataByAccount) {

        return "ERR";
    }

}
