package com.example.slmiddleware.domain;


import com.example.slmiddleware.dto.EmptyProductionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process_TB,Long> {


   // @Query(value = "SELECT count(*) AS PRD_AMT,count(*)-count(ERR_CD) AS FAIR_PRD_AMT,count(ERR_CD) AS ERR_PRD_AMT,WKCTR_CD,SYSDATE AS CREATE_DT FROM process_TB P WHERE TO_CHAR(CREATE_DT_06,'yyyymmdd')=TO_CHAR(SYSDATE,'yyyymmdd') GROUP BY (WKCTR_CD)",nativeQuery = true)
//    @Query(value = "SELECT \n" +
//            "count(pr.PRC_SQ) AS PRD_AMT,\n" +
//            "count(pr.PRC_SQ)-count(ERR_CD) AS FAIR_PRD_AMT,\n" +
//            "count(ERR_CD) AS ERR_PRD_AMT,\n" +
//            "wk.WKCTR_CD,\n" +
//            "SYSDATE AS CREATE_DT\n" +
//            "FROM WKCTR_TB wk LEFT OUTER JOIN PROCESS_TB pr ON wk.WKCTR_CD = pr.WKCTR_CD\n" +
//            "WHERE TRUNC(TO_DATE(pr.CREATE_DT_06,'YYYY-MM-DD HH24:MI:SS')) = TRUNC(SYSDATE) OR pr.PRC_CD_01 IS null\n" +
//            "GROUP BY (wk.WKCTR_CD)\n" +
//            ";",nativeQuery = true)
//    @Query(value = "SELECT count(pr.PRC_SQ) AS PRD_AMT, count(pr.PRC_SQ)-count(ERR_CD) AS FAIR_PRD_AMT, count(ERR_CD) AS ERR_PRD_AMT, wk.WKCTR_CD, SYSDATE AS PRD_DT FROM  PROCESS_TB pr RIGHT OUTER JOIN WKCTR_TB wk ON wk.WKCTR_CD = pr.WKCTR_CD WHERE TRUNC(TO_DATE(pr.CREATE_DT_06,'YYYY-MM-DD HH24:MI:SS')) = TRUNC(SYSDATE) OR pr.PRC_CD_01 IS null GROUP BY (wk.WKCTR_CD)",nativeQuery = true)
//    List<Production_Day_TB> findSum();
}
