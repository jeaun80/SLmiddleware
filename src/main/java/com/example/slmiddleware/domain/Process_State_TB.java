package com.example.slmiddleware.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process_State_TB extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long STATE_SQ;

    @Column
    private Long PRC_SQ;
    private String ERR_CD;
    private String ERR_DT;
    private String END_DT;
}
