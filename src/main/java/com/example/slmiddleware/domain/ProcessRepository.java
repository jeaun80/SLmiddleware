package com.example.slmiddleware.domain;


import com.example.slmiddleware.dto.EmptyProductionDto;
import com.example.slmiddleware.dto.ProductionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process_TB,Long> {

    @Query(value="SELECT count(pr.PRC_SQ) AS PRD_AMT, count(pr.PRC_SQ)-count(ERR_CD) AS FAIR_PRD_AMT, count(ERR_CD) AS ERR_PRD_AMT, wk.WKCTR_CD AS WKCTR_CD, NOW() AS PRD_DT\n" +
            "FROM  PROCESS_TB pr RIGHT OUTER JOIN WKCTR_TB wk ON wk.WKCTR_CD = pr.WKCTR_CD\n" +
            "WHERE DATE_format(now(),'%Y-%m-%d')=date_format(create_dt_06,'%Y-%m-%d') OR pr.PRC_CD_01 IS null\n" +
            "GROUP BY (wk.WKCTR_CD);",nativeQuery = true)
    public List<ProductionDto> findSum();
}
