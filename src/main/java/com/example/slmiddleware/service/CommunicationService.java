package com.example.slmiddleware.service;

import com.example.slmiddleware.domain.Process;
import com.example.slmiddleware.domain.ProcessMapper;
import com.example.slmiddleware.domain.ProcessRepository;
import com.example.slmiddleware.dto.ResponseProcessMsgDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.json.JsonObject;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONObject;

@Service
@RequiredArgsConstructor
public class CommunicationService {

    private final ProcessRepository processRepository;
    private final Queue<Process> queue = new LinkedList<>();
    public void test(Object message) throws JsonProcessingException {

        JSONObject object = new JSONObject(message);
        String errcode = object.get("ERR_CD").toString();
//        if(errcode!="0"){
//            //에러코드 핸들링 클래스를 따로 만들자.
//        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //선언한 필드만 매핑
        String jsonSting = objectMapper.writeValueAsString(message);
        ResponseProcessMsgDto msg[] = objectMapper.readValue(jsonSting,ResponseProcessMsgDto[].class);
        System.out.println(message);

        Process process = ProcessMapper.MAPPER.toEntity(msg[0]);
        queue.add(process);

        //큐 데이터를 세이브하는동안 다른데이터가 들어올경우


        //큐에 넣고 일정시간이 지나면 세이브올
        //일정시간이 안지나도 큐에 가득차면 세이브올


        if(queue.size()>=50){
            processRepository.saveAll(queue);
            queue.clear();
            // saveall이 아니라 union all 쿼리문 작성해서 jpa 함수만든다.
        }
    }

    public void t(){



    }


    //1공정부터 6공정까지 한번에 준다.
    //파싱해서 테이블마다 객체로저장한다.
    //테이블객체 or 오브젝트타입 으로 큐를 생성한다.
    //큐가 일정이상 넘으면 저장한다. (union all  or insert all 이용)
    // 데이터를 큐에 받아서 큐가 1000이 되면 저장하게한다.
    // 큐를 한개만 만드는게아니라
}
