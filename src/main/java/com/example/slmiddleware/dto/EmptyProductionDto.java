package com.example.slmiddleware.dto;

import lombok.*;
import oracle.sql.DATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmptyProductionDto {

//    String getWKCTR_CD();
//    long getPRD_AMT();
//    long getFAIR_PRD_AMT();
//    long getERR_PRD_AMT();
//    String getPRD_DT();
//    private
    private String WKCTR_CD;
    private long PRD_AMT;
    private long FAIR_PRD_AMT;
    private long ERR_PRD_AMT;
    private String PRD_DT;
}
