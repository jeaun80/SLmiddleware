package com.example.slmiddleware.controller;

import com.example.slmiddleware.service.CommunicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class DataScheduledController {

    private final CommunicationService communicationService;
    @Scheduled(cron = "0 55 23 * * *", zone = "Asia/Seoul")
    public void sum(){
        try{
            communicationService.sum();
        }catch (Exception e){
            log.error("일별생산량 집계 오류"+ e.getMessage());
        }
    }
    @Scheduled(fixedDelay = 60000,initialDelay = 0)
    public void timeout(){
        try{
            communicationService.timeOut();
            log.debug("timeout");
        }catch (Exception e){
            log.error("timeoutControllerError : "+ e.getMessage());
        }
    }
}
