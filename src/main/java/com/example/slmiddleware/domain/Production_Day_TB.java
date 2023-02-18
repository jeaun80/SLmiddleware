package com.example.slmiddleware.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Production_Day_TB extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PRODUCTION_DAY_SQ;

    @Column
    private String WKCTR_CD;
    private long PRD_AMT;
    private long FAIR_PRD_AMT;
    private long ERR_PRD_AMT;
    private String PRD_DT;


}
