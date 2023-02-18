package com.example.slmiddleware.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Test_TB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column
    private String TEST_DT;
    private int TEST_NUMBER;
    private String TEST_STRING;


}
