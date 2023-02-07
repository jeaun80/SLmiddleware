package com.example.slmiddleware.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oracle.sql.DATE;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.vladmihalcea.hibernate.id.BatchSequenceGenerator;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Process_TB extends BaseTimeEntity{

    @Id
//    @GenericGenerator(
//            name = "SequenceGenerator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "PROCESS_seq"),
//                    @Parameter(name = "optimizer", value = "pooled"),
//                    @Parameter(name = "initial_value", value = "1"),
//                    @Parameter(name = "increment_size", value = "100")
//            }
//
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "SequenceGenerator"
//    )

    @GenericGenerator(
            name = "PROCESS_seq",
            strategy = "com.vladmihalcea.hibernate.id.BatchSequenceGenerator",
            parameters = {
                    @Parameter(name = "sequence", value = "PROCESS_seq"),
                    @Parameter(name = "fetch_size", value = "1000")
            }
    )
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

    private String ERR_CD;
    private DATE ERR_DT;
    private String ERR_PRC_CD;


}
