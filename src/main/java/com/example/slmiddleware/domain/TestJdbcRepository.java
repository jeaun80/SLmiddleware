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
public class TestJdbcRepository {


    private int batchSize = 30;
    private final JdbcTemplate jdbcTemplate;

    public void JdbcsaveAll(Queue<Test_TB> items) {
        int batchCount = 0;
        List<Test_TB> subItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            System.out.println("fhasdifasdfkjasdkfjasdkfljasdlk;fjasdl;kffsdjklksa"+items.peek().getTEST_DT());
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

    private int batchInsert(int batchCount, List<Test_TB> subItems) {//42개
        jdbcTemplate.batchUpdate("INSERT INTO test_tb (test_dt,test_number,test_string) VALUES (?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, subItems.get(i).getTEST_DT());
                ps.setInt(2, subItems.get(i).getTEST_NUMBER());
                ps.setString(3, subItems.get(i).getTEST_STRING());
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
