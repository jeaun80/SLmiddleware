package com.example.slmiddleware.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessStateRepository extends JpaRepository<Process_State_TB,Long> {

}
