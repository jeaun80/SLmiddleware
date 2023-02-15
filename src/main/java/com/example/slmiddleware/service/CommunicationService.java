package com.example.slmiddleware.service;

import com.example.slmiddleware.domain.*;
import com.example.slmiddleware.dto.EmptyProductionDto;
import com.example.slmiddleware.dto.ProductionDto;
import com.example.slmiddleware.dto.ResponseProcessMsgDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import oracle.sql.DATE;
import org.apache.activemq.artemis.json.JsonObject;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Log4j2
@Service
@RequiredArgsConstructor
public class CommunicationService {
    private final ProcessJdbcRepository processJdbcRepository;
    private final testRepository testRepository;
    private final TestJdbcRepository testJdbcRepository;
    private final ProcessRepository processRepository;
    private final ProcessStateRepository processStateRepository;
    private final ProductionDayRepository productionDayRepository;
    private final Queue<Process_TB> queue = new LinkedList<>();
    private StopWatch stopWatch = new StopWatch();



    public void parsing(Object message) throws JsonProcessingException {
        if(!stopWatch.isRunning()){
            stopWatch.start();
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //선언한 필드만 매핑
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            ResponseProcessMsgDto dto = objectMapper.readValue(message.toString(), ResponseProcessMsgDto.class);
            if(dto.getERR_CD()!=null){
                Process_TB errprocess = ProcessMapper.MAPPER.toEntity(dto);
                errorSave(errprocess);
            }
            Process_TB process = ProcessMapper.MAPPER.toEntity(dto);
            log.error("queSize"+queue.size());
            log.error("why");
            System.out.println("why2");
            queueStore(process);
            System.out.println("whyafter");
//            if(process.getERR_CD()!=null){
//                errorSave(process);
//            }
        }catch (Exception e){
            stopWatch.stop();
            log.debug("parsing error"+ e.getMessage());
        }
    }


    public void queueStore(Process_TB msg){
        try{
            queue.add(msg);
            if(queue.size()>=30){
                processSave();
            }
        }catch (Exception e){
            log.debug("queueStore : "+ e.getMessage());
        }
    }

    @Transactional
    public void processSave(){
        try{
            processRepository.saveAll(queue);
//            processJdbcRepository.JdbcsaveAll(queue);
            queue.clear();
            stopWatch.stop();
            log.info("process save");
        }catch (Exception e){
            log.error("processsaveError : "+ e.getMessage() );
        }
    }

    public void errorSave(Process_TB errorprocess){
        try {
            Process_State_TB errPro= Process_State_TB.builder()
                    .ERR_CD(errorprocess.getERR_CD())
                    .ERR_DT(errorprocess.getERR_DT())
                    .END_DT(errorprocess.getERR_DT())
                    .PRC_SQ(errorprocess.getPRC_SQ())
                    .build();
            processStateRepository.save(errPro);
            log.debug("errorSave Id : "+ errorprocess.getPRC_SQ());

        } catch (Exception e) {
            log.error("errorSave : "+ e.getMessage());
        }
    }

    public boolean timeOut(){
        try{
            if(stopWatch.isRunning()){
                if(stopWatch.getTotalTimeSeconds()>60 && !queue.isEmpty()){
                    processSave();
                    log.debug("timeoutMessage");
                    return true;
                }
            }
        }catch (Exception e){
            log.error("timeOut : " +e.getMessage());
            return false;
        }
        return false;
    }
    public void sum(){
        List<ProductionDto> production_day_tb = processRepository.findSum();
        if(!production_day_tb.isEmpty()){
            List<Production_Day_TB> production_day_tbs= new ArrayList<>();
            for(ProductionDto i : production_day_tb){
                Production_Day_TB production_day_tb1= Production_Day_TB.builder()
                        .ERR_PRD_AMT(i.getERR_PRD_AMT())
                        .FAIR_PRD_AMT(i.getFAIR_PRD_AMT())
                        .PRD_AMT(i.getPRD_AMT())
                        .WKCTR_CD(i.getWKCTR_CD())
                        .PRD_DT(i.getPRD_DT())
                        .build();
                production_day_tbs.add(production_day_tb1);
            }
            log.info("일별 생산량 집계 : "+productionDayRepository.saveAll(production_day_tbs).toString());
        }
        else{
            log.error("db error");
        }
    }
    public void test(String a) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //선언한 필드만 매핑
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        Test_TB dto = objectMapper.readValue(a, Test_TB.class);

        Queue<Test_TB> testque = new LinkedList<>();
        for(int i=0;i<30;i++){
            testque.add(dto);
        }
        testJdbcRepository.JdbcsaveAll(testque);
        System.out.println(a);
//        DATE date = new DATE("2023-02-09");
        System.out.println(dto.getTEST_DT());
        System.out.println(dto.getTEST_STRING());
        System.out.println(dto.getTEST_NUMBER());
        testRepository.save(dto);
//        testRepository.test();
    }
}
