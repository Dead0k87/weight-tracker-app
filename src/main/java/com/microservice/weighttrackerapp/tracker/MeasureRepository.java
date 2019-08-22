package com.microservice.weighttrackerapp.tracker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasureRepository extends JpaRepository<Measure, Long> {

    public List<Measure> findByName(String name);
}
