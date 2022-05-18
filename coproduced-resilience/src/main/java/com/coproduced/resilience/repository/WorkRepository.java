package com.coproduced.resilience.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coproduced.resilience.entity.Work;

public interface WorkRepository extends JpaRepository<Work, Long> {
	
	public Work findByWorkCode(String workCode);
}
