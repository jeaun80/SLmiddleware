package com.example.slmiddleware.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oracle.sql.DATE;

import java.util.Date;

@Entity
@Getter
@Setter
public class Test_TB {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long ID;

//    @Temporal(TemporalType.TIME)
    private String TEST_DT;
    private int TEST_NUMBER;
    private String TEST_STRING;


}
