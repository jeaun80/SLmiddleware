package com.example.slmiddleware.service;

import com.example.slmiddleware.domain.*;
import com.example.slmiddleware.dto.EmptyProductionDto;
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
import org.springframework.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CommunicationService {
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(CommunicationService.class);
    private final testRepository testRepository;
    private final ProcessRepository processRepository;
    private final ProcessStateRepository processStateRepository;
    private final ProductionDayRepository productionDayRepository;
    private final Queue<Process_TB> queue = new LinkedList<>();
    private ResponseProcessMsgDto[] msg;
    private StopWatch stopWatch = new StopWatch();



    public void parsing(Object message) throws JsonProcessingException {

        if(queue.isEmpty()){
            stopWatch.start();
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //선언한 필드만 매핑
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            ResponseProcessMsgDto dto = objectMapper.readValue(message.toString(), ResponseProcessMsgDto.class);
            System.out.println(dto.getCREATE_DT_01());
            System.out.println(dto.getWKCTR_CD());
            Process_TB process = ProcessMapper.MAPPER.toEntity(dto);
            queueStore(process);
            logger.info("데이터 갯수 "+queue.size());
            logger.info(stopWatch.isRunning()+"");
        }catch (Exception e){
            stopWatch.stop();
            e.printStackTrace();
        }
    }


    public void queueStore(Process_TB msg){
        try{
            queue.add(msg);
            if(queue.size()>=30 || stopWatch.getTotalTimeSeconds()>60){
                processSave();
                logger.info("저장합니다.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void processSave(){
        try{
            processRepository.saveAll(queue);
            queue.clear();
            stopWatch.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void errorHanding(Object msg){
        Process_State_TB process_state_tb= new Process_State_TB();
        try {
            JSONObject object = new JSONObject(msg);
            process_state_tb.setERR_CD(object.get("ERR_CD").toString());
            process_state_tb.setERR_DT((DATE)object.get("ERR_DT"));
            process_state_tb.setEND_DT((DATE)object.get("ERR_DT"));
            process_state_tb.setPRC_SQ(object.getLong("ERR_PRC_CD"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            processStateRepository.save(process_state_tb);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    @Scheduled(fixedDelay = 60000,initialDelay = 1000)
    public void timeOut(){
        try{
            if(stopWatch.isRunning()){
                if(stopWatch.getTotalTimeSeconds()>55){
                    processSave();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    @Scheduled(cron = "0 0 23 * * *", zone = "Asia/Seoul")
    public void sum(){
        String sql = "SELECT count(pr.PRC_SQ) AS PRD_AMT, count(pr.PRC_SQ)-count(ERR_CD) AS FAIR_PRD_AMT, count(ERR_CD) AS ERR_PRD_AMT, wk.WKCTR_CD, SYSDATE AS PRD_DT FROM  PROCESS_TB pr RIGHT OUTER JOIN WKCTR_TB wk ON wk.WKCTR_CD = pr.WKCTR_CD WHERE TRUNC(TO_DATE(pr.CREATE_DT_06,'YYYY-MM-DD HH24:MI:SS')) = TRUNC(SYSDATE) OR pr.PRC_CD_01 IS null GROUP BY (wk.WKCTR_CD)";
            Query nativeQuery = em.createNativeQuery(sql, EmptyProductionDto.class);
            try {
                List<EmptyProductionDto> production_day_tb = nativeQuery.getResultList();
                if(production_day_tb.isEmpty()){
                    logger.info("비었음");
                }
                else{
                    System.out.println(production_day_tb.get(0).toString());
                    for(Object i : production_day_tb){
                        System.out.println(i);
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
                logger.error("z쿼리부터 오류");
            }
//        List<Production_Day_TB> production_day_tb = processRepository.findSum();
//        if(!production_day_tb.isEmpty()){
//            logger.error("집계성공");
//
////            productionDayRepository.saveAll(prodution_day_tb);
//        }
//        else{
//            logger.error("일별 생산량 집계 오류");
//        }
    }
    public void t(String a) throws JsonProcessingException {//postman으로 json파싱 검사를 위한 메서드
        String j = "{\n" +
                "    \"WKCTR_CD\" : \"FA1WK1\",\n" +
                "    \"PRC_CD_01\" : \"FA1WK1PR1\",\n" +
                "    \"DATA_A_01\" : 10,\n" +
                "    \"DATA_B_01\": null,\n" +
                "    \"QUALITY_01\" : 1,\n" +
                "    \"CREATE_DT_01\" : \"2023-01-15 17:04:19.07\",\n" +
                "    \"END_DT_01\" : \"2023-01-15 17:04:43.07\",\n" +
                "    \"PRC_CD_02\" : \"FA1WK1PR1\",\n" +
                "    \"DATA_A_02\" : 10,\n" +
                "    \"DATA_B_02\" : null,\n" +
                "    \"QUALITY_02\" : 1,\n" +
                "    \"CREATE_DT_02\" : \"2023-01-15 17:04:19.07\",\n" +
                "    \"END_DT_02\" : \"2023-01-15 17:04:43.07\",\n" +
                "    \"PRC_CD_03\" : \"FA1WK1PR1\",\n" +
                "    \"DATA_A_03\" : 10,\n" +
                "    \"DATA_B_03\" : null,\n" +
                "    \"QUALITY_03\" : 1,\n" +
                "    \"CREATE_DT_03\" : \"2023-01-15 17:04:19.07\",\n" +
                "    \"END_DT_03\" : \"2023-01-15 17:04:43.07\",\n" +
                "    \"PRC_CD_04\" : \"FA1WK1PR1\",\n" +
                "    \"DATA_A_04\" : 10,\n" +
                "    \"DATA_B_04\" : null,\n" +
                "    \"QUALITY_04\" : 1,\n" +
                "    \"CREATE_DT_04\" : \"2023-01-15 17:04:19.07\",\n" +
                "    \"END_DT_04\" : \"2023-01-15 17:04:43.07\",\n" +
                "    \"PRC_CD_05\" : \"FA1WK1PR1\",\n" +
                "    \"DATA_A_05\" : 10,\n" +
                "    \"DATA_B_05\" : null,\n" +
                "    \"QUALITY_05\" : 1,\n" +
                "    \"CREATE_DT_05\" : \"2023-01-15 17:04:19.07\",\n" +
                "    \"END_DT_05\" : \"2023-01-15 17:04:43.07\",\n" +
                "    \"PRC_CD_06\" : \"FA1WK1PR1\",\n" +
                "    \"DATA_A_06\" : 10,\n" +
                "    \"DATA_B_06\" : null,\n" +
                "    \"QUALITY_06\" : 1,\n" +
                "    \"CREATE_DT_06\" : \"2023-01-15 17:04:19.07\",\n" +
                "    \"END_DT_06\" : \"2023-01-15 17:04:43.07\",\n" +
                "    \"ERR_CD\" : null,\n" +
                "    \"ERR_DT\" : null,\n" +
                "    \"ERR_PRC_CD\" : null\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //선언한 필드만 매핑
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        ResponseProcessMsgDto dto = objectMapper.readValue(a, ResponseProcessMsgDto.class);
        System.out.println(a);
        System.out.println(dto.getCREATE_DT_01());
        System.out.println(dto.getWKCTR_CD());
        Process_TB process = ProcessMapper.MAPPER.toEntity(dto);
        processRepository.save(process);
    }
    public void test(String a) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //선언한 필드만 매핑
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        Test_TB dto = objectMapper.readValue(a, Test_TB.class);
        System.out.println(a);
//        DATE date = new DATE("2023-02-09");
        System.out.println(dto.getTEST_DT());
        System.out.println(dto.getTEST_STRING());
        System.out.println(dto.getTEST_NUMBER());
        testRepository.save(dto);
    }
}
