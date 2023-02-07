package com.example.slmiddleware.service;

import com.example.slmiddleware.domain.*;
import com.example.slmiddleware.dto.ResponseProcessMsgDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import oracle.sql.DATE;
import org.apache.activemq.artemis.json.JsonObject;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONObject;
import org.springframework.util.StopWatch;

@Service
@RequiredArgsConstructor
public class CommunicationService {

    private final ProcessRepository processRepository;
    private final ProcessStateRepository processStateRepository;
    private final Queue<Process_TB> queue = new LinkedList<>();
    private ResponseProcessMsgDto[] msg;
    private StopWatch stopWatch = new StopWatch();



    public void parsing(Object message) throws JsonProcessingException {

        if(queue.isEmpty()){
            stopWatch.start();
        }
        try{
            JSONObject object = new JSONObject(message);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //선언한 필드만 매핑
            String jsonString = objectMapper.writeValueAsString(message);
            msg = objectMapper.readValue(jsonString,ResponseProcessMsgDto[].class);

            if(object.get("ERR_CD")!=null){
                errorHanding(message);
            }
            for(ResponseProcessMsgDto s : msg){
                Process_TB process = ProcessMapper.MAPPER.toEntity(s);
                queueStore(process);
            }
        }catch (Exception e){
            stopWatch.stop();
            e.printStackTrace();
        }
    }


    public void queueStore(Process_TB msg){
        try{
            queue.add(msg);
            if(queue.size()>=100 || stopWatch.getTotalTimeSeconds()>60){
                processRepository.saveAll(queue);
                queue.clear();
                stopWatch.stop();
            }
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
        msg = objectMapper.readValue(a,ResponseProcessMsgDto[].class);
        System.out.println(Arrays.toString(msg));

    }
}
