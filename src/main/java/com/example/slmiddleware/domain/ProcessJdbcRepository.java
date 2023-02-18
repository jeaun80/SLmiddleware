package com.example.slmiddleware.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Repository
@RequiredArgsConstructor
public class ProcessJdbcRepository {

    private int batchSize = 30;
    private final JdbcTemplate jdbcTemplate;

    public void JdbcsaveAll(Queue<Process_TB> items) {
        int batchCount = 0;
        List<Process_TB> subItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            System.out.println("fhasdifasdfkjasdkfjasdkfljasdlk;fjasdl;kffsdjklksa"+items.peek().getCREATE_DT_01());
            subItems.add(items.poll());
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsert(batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsert(batchCount, subItems);
        }
        System.out.println("batchCount: " + batchCount);
    }

    private int batchInsert(int batchCount, List<Process_TB> subItems) {//42개
        jdbcTemplate.batchUpdate("INSERT INTO PROCESS_TB (PRC_CD_01,QUALITY_01,CREATE_DT_01,END_DT_01,DATA_A_01,DATA_B_01,PRC_CD_02,QUALITY_02,CREATE_DT_02,END_DT_02,DATA_A_02,DATA_B_02,PRC_CD_03,QUALITY_03,CREATE_DT_03,END_DT_03,DATA_A_03,DATA_B_03,PRC_CD_04,QUALITY_04,CREATE_DT_04,END_DT_04,DATA_A_04,DATA_B_04,PRC_CD_05,QUALITY_05,CREATE_DT_05,END_DT_05,DATA_A_05,DATA_B_05,PRC_CD_06,QUALITY_06,CREATE_DT_06,END_DT_06,DATA_A_06,DATA_B_06,ERR_CD,ERR_DT,ERR_PRC_CD,WKCTR_CD,CREATE_DT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, subItems.get(i).getPRC_CD_01());
                ps.setInt(2, subItems.get(i).getQUALITY_01());
                ps.setString(3, subItems.get(i).getCREATE_DT_01());
                ps.setString(4, subItems.get(i).getEND_DT_01());
                ps.setInt(5, subItems.get(i).getDATA_A_01());
                ps.setInt(6, subItems.get(i).getDATA_B_01());
                ps.setString(7, subItems.get(i).getPRC_CD_02());
                ps.setInt(8, subItems.get(i).getQUALITY_02());
                ps.setString(9, subItems.get(i).getCREATE_DT_02());
                ps.setString(10, subItems.get(i).getEND_DT_02());
                ps.setInt(11, subItems.get(i).getDATA_A_02());
                ps.setInt(12, subItems.get(i).getDATA_B_02());
                ps.setString(13, subItems.get(i).getPRC_CD_03());
                ps.setInt(14, subItems.get(i).getQUALITY_03());
                ps.setString(15, subItems.get(i).getCREATE_DT_03());
                ps.setString(16, subItems.get(i).getEND_DT_03());
                ps.setInt(17, subItems.get(i).getDATA_A_03());
                ps.setInt(18, subItems.get(i).getDATA_B_03());
                ps.setString(19, subItems.get(i).getPRC_CD_04());
                ps.setInt(20, subItems.get(i).getQUALITY_04());
                ps.setString(21, subItems.get(i).getCREATE_DT_04());
                ps.setString(22, subItems.get(i).getEND_DT_04());
                ps.setInt(23, subItems.get(i).getDATA_A_04());
                ps.setInt(24, subItems.get(i).getDATA_B_04());
                ps.setString(25, subItems.get(i).getPRC_CD_05());
                ps.setInt(26, subItems.get(i).getQUALITY_05());
                ps.setString(27, subItems.get(i).getCREATE_DT_05());
                ps.setString(28, subItems.get(i).getEND_DT_05());
                ps.setInt(29, subItems.get(i).getDATA_A_05());
                ps.setInt(30, subItems.get(i).getDATA_B_05());
                ps.setString(31, subItems.get(i).getPRC_CD_06());
                ps.setInt(32, subItems.get(i).getQUALITY_06());
                ps.setString(33, subItems.get(i).getCREATE_DT_06());
                ps.setString(34, subItems.get(i).getEND_DT_06());
                ps.setInt(35, subItems.get(i).getDATA_A_06());
                ps.setInt(36, subItems.get(i).getDATA_B_06());
                ps.setString(37, subItems.get(i).getERR_CD());
                ps.setString(38, subItems.get(i).getERR_DT());
                ps.setString(39, subItems.get(i).getERR_PRC_CD());
                ps.setString(40, subItems.get(i).getWKCTR_CD());
                ps.setString(41, subItems.get(i).getCREATE_DT());
            }
            @Override
            public int getBatchSize() {
                return subItems.size();
            }
        });
        subItems.clear();
        batchCount++;
        return batchCount;
    }

}
