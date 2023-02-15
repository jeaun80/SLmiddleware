package com.example.slmiddleware.domain;

import jakarta.persistence.*;
import lombok.*;
import oracle.sql.DATE;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Production_Day_TB extends BaseTimeEntity{
    @Id
//    @GenericGenerator(
//            name = "PRODUCTION_DAY_seq",
//            strategy = "com.vladmihalcea.hibernate.id.BatchSequenceGenerator",
//            parameters = {
//                    @org.hibernate.annotations.Parameter(name = "sequence", value = "PRODUCTION_DAY_seq"),
//                    @Parameter(name = "fetch_size", value = "1")
//            }
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "PRODUCTION_DAY_seq"
//    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PRODUCTION_DAY_SQ;
    @Column
    private String WKCTR_CD;
    private long PRD_AMT;
    private long FAIR_PRD_AMT;
    private long ERR_PRD_AMT;
    private String PRD_DT;


}
