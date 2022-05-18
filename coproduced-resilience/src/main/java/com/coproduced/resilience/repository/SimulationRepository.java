package com.coproduced.resilience.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coproduced.resilience.entity.Simulation;

public interface SimulationRepository extends JpaRepository<Simulation, Long> {

}
