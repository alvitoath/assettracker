package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import propensi.project.Assettrackr.service.DummyService;

@Controller
public class PageController {
    private WebClient webClient = WebClient.builder().build();
    @Autowired
    DummyService dummyService;

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/login")
    public String login() {
//        dummyService.createUser();
        return "login";
    }

}
