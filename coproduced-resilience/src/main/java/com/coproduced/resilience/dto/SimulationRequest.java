package com.coproduced.resilience.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class SimulationRequest {

	Long idParticipant;
	Long idWork;
	Long simulationNumber;
	Double socialResilience;
	Double infraestructureResilience;
}
