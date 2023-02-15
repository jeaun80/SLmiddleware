package com.example.slmiddleware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface testRepository extends JpaRepository<Test_TB,Long> {


//    @Query(value = "select TEST_DT from test_tb;",nativeQuery = true)
//    public Optional<Test_TB> test();
}
