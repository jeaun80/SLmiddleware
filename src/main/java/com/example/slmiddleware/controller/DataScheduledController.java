package com.example.slmiddleware.controller;

import com.example.slmiddleware.service.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataScheduledController {

    private final CommunicationService communicationService;
    private final Logger log = LoggerFactory.getLogger(DataScheduledController.class);

    @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Seoul")
    public void sum(){
        try{
            communicationService.sum();
        }catch (Exception e){
            e.printStackTrace();
            log.error("");
        }
    }
//    @Scheduled(fixedDelay = 60000,initialDelay = 0)
//    public void timeout(){
//        communicationService.timeOut();
//        communicationService.sum();
//        System.out.println("........si");
//
//    }
}
