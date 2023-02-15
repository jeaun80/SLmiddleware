package com.example.slmiddleware.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmptyProductionDto {

//    String getWKCTR_CD();
//    long getPRD_AMT();
//    long getFAIR_PRD_AMT();
//    long getERR_PRD_AMT();
//    String getPRD_DT();
//    private
    private String WKCTR_CD;
    private int PRD_AMT;
    private int FAIR_PRD_AMT;
    private int ERR_PRD_AMT;
    private String PRD_DT;
}
