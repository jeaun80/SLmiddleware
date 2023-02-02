package com.example.slmiddleware.controller;

import com.example.slmiddleware.service.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunicationController {

    private final CommunicationService communicationService;
    @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Seoul")
    public void t(){

        communicationService.t();

    }
}
