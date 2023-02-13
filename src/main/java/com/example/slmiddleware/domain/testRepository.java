package com.example.slmiddleware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface testRepository extends JpaRepository<Test_TB,Long> {


//    @Query(value = "select * from Test_TB",nativeQuery = true)
//    public Object test();
}
