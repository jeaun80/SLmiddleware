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
public class Process_State_TB extends BaseTimeEntity{
    @Id
//    @GenericGenerator(
//            name = "PROCESS_STATE_seq",
//            strategy = "com.vladmihalcea.hibernate.id.BatchSequenceGenerator",
//            parameters = {
//                    @Parameter(name = "sequence", value = "PROCESS_STATE_seq"),
//            }
//    )
//        @GenericGenerator(
//            name = "StateSequenceGenerator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "PROCESS_STATE_seq")
//            }
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "StateSequenceGenerator"
//    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long STATE_SQ;

    @Column
    private Long PRC_SQ;
    private String ERR_CD;
    private String ERR_DT;
    private String END_DT;
}
