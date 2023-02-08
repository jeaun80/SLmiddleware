package com.example.slmiddleware.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oracle.sql.DATE;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Process_State_TB extends BaseTimeEntity{
    @Id
        @GenericGenerator(
            name = "StateSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "STATE_SQ")
            }
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "StateSequenceGenerator"
    )
    private long STATE_SQ;

    @Column
    private long PRC_SQ;
    private String ERR_CD;
    private DATE ERR_DT;
    private DATE END_DT;
}
