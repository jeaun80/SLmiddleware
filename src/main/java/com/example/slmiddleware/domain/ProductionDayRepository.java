package com.example.slmiddleware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductionDayRepository extends JpaRepository<Production_Day_TB,Long> {

}
