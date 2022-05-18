package com.coproduced.resilience.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.coproduced.resilience.dto.ParticipantRequest;
import com.coproduced.resilience.dto.SimulationRequest;
import com.coproduced.resilience.entity.Participant;
import com.coproduced.resilience.entity.Simulation;
import com.coproduced.resilience.entity.Work;
import com.coproduced.resilience.repository.ParticipantRepository;
import com.coproduced.resilience.repository.SimulationRepository;
import com.coproduced.resilience.repository.WorkRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/coproduced-resilience")
public class CoproducedResilienceController {

	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private SimulationRepository simulationRepository;

	@GetMapping("/work")
	public Work getNewWork() {
		Work work = workRepository.save(new Work());
		work.setWorkCode("P" + String.format("%03d", work.getId()));
		workRepository.save(work);
		return work;
	}

	@PostMapping("/participant")
	public Participant newParticipant(@RequestBody ParticipantRequest participantRequest) {
		System.out.println(participantRequest);
		Work work = workRepository.findByWorkCode(participantRequest.getWorkCode());
		if(work == null) {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "Work not found", new Exception());
		}
		System.out.println(work);
		Participant participant = new Participant(null, participantRequest.getFullName(), work);
		System.out.println(participant);
		return participantRepository.save(participant);
	}

	@PostMapping("/simulation")
	public Simulation newSimulation(@RequestBody SimulationRequest simulationRequest) {
		System.out.println(simulationRequest);
		Work work = workRepository.getById(simulationRequest.getIdWork());
		System.out.println(work);

		Participant participant = participantRepository.getById(simulationRequest.getIdParticipant());
		System.out.println(participant);

		Double result = getResult(simulationRequest.getSimulationNumber(), simulationRequest.getSocialResilience(),
				simulationRequest.getInfraestructureResilience());
		String descripcionResult = getDescriptionResult(result);

		return simulationRepository.save(new Simulation(null, participant, work,
				simulationRequest.getSimulationNumber(), simulationRequest.getSocialResilience(),
				simulationRequest.getInfraestructureResilience(), result, descripcionResult));
	}

	private String getDescriptionResult(Double result) {
		if(result > 3) {
			return "Balance";
		}
		return "Needs invest more in social";
	}

	Double WF[] = {0.5, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
	
	private Double getResult(Long simulationNumber, Double socialResilience, Double infraestructureResilience) {
		Double wf = WF[(int) (simulationNumber - 1)];
		
		Double normalWeigtedResilience = (socialResilience * (wf / (1 + wf))) + (infraestructureResilience * (1 / (1 + wf)));
		Double imbalancePenalty = 4 / ((socialResilience + infraestructureResilience) * ((1 / socialResilience) + (1 / infraestructureResilience)));
		Double overallResilience = normalWeigtedResilience * imbalancePenalty;
		
		System.out.println("normalWeigtedResilience " + normalWeigtedResilience);
		System.out.println("imbalancePenalty " + imbalancePenalty);
		System.out.println("overallResilience " + overallResilience);
		
		return overallResilience;
	}
	
	@GetMapping("/simulation/all")
	public List<Simulation> getSimulations() {
		return simulationRepository.findAll();
	}
}
