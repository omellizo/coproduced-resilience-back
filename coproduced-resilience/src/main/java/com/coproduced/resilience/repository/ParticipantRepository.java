package com.coproduced.resilience.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coproduced.resilience.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
