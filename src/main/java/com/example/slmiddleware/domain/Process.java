package com.example.slmiddleware.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oracle.sql.DATE;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Process extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "xxx_SEQUENCE_GENERATOR")
    @SequenceGenerator(name="xxx_SEQUENCE_GENERATOR", sequenceName = "xxx_SEQUENCE", initialValue = 1, allocationSize = 1)
    private Long PRC_SQ;

    @Column
    private String WKCTR_CD;
    private String PRC_CD_01;
    private int QUALITY_01;
    private DATE CREATE_DT_01;
    private int DATA_A_01;
    private int DATA_B_01;
    private DATE END_DT_01;

    private String PRC_CD_02;
    private int QUALITY_02;
    private DATE CREATE_DT_02;
    private int DATA_A_02;
    private int DATA_B_02;
    private DATE END_DT_02;

    private String PRC_CD_03;
    private int QUALITY_03;
    private DATE CREATE_DT_03;
    private int DATA_A_03;
    private int DATA_B_03;
    private DATE END_DT_03;

    private String PRC_CD_04;
    private int QUALITY_04;
    private DATE CREATE_DT_04;
    private int DATA_A_04;
    private int DATA_B_04;
    private DATE END_DT_04;

    private String PRC_CD_05;
    private int QUALITY_05;
    private DATE CREATE_DT_05;
    private int DATA_A_05;
    private int DATA_B_05;
    private DATE END_DT_05;

    private String PRC_CD_06;
    private int QUALITY_06;
    private DATE CREATE_DT_06;
    private int DATA_A_06;
    private int DATA_B_06;
    private DATE END_DT_06;

}
