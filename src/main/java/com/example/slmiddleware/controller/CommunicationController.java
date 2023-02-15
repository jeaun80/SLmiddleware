package com.example.slmiddleware.controller;

import com.example.slmiddleware.service.CommunicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunicationController {

    private final CommunicationService communicationService;
//    @PostMapping("/hi")
//    public void t(@RequestBody String testmsg) throws JsonProcessingException {
//        communicationService.t(testmsg);
//    }
    @PostMapping("/test")
    public void test(@RequestBody String testmsg) throws JsonProcessingException {
        communicationService.test(testmsg);
    }
}
