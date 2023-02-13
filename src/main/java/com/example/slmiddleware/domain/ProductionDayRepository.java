package com.example.slmiddleware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionDayRepository extends JpaRepository<Production_Day_TB,Long> {

//    @Query(value = "SELECT \n" +
//            "count(pr.PRC_SQ) AS PRD_AMT,\n" +
//            "count(pr.PRC_SQ)-count(ERR_CD) AS FAIR_PRD_AMT,\n" +
//            "count(ERR_CD) AS ERR_PRD_AMT,\n" +
//            "wk.WKCTR_CD,\n" +
//            "SYSDATE AS PRD_DT\n" +
//            "FROM  PROCESS_TB pr RIGHT OUTER JOIN WKCTR_TB wk ON wk.WKCTR_CD = pr.WKCTR_CD\n" +
//            "WHERE TRUNC(TO_DATE(pr.CREATE_DT_06,'YYYY-MM-DD HH24:MI:SS')) = TRUNC(SYSDATE) OR pr.PRC_CD_01 IS null\n" +
//            "GROUP BY (wk.WKCTR_CD)\n" +
//            ";",nativeQuery = true)
//    List<Production_Day_TB> findSum();
}
