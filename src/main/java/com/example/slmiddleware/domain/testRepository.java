package com.example.slmiddleware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface testRepository extends JpaRepository<Test_TB,Long> {

}
