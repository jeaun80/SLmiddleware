package com.example.slmiddleware.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunicationService {

    public void test(Object message){
        System.out.println(message);
    }
}
