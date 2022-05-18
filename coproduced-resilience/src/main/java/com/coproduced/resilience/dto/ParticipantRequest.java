package com.coproduced.resilience.dto;

import java.io.Serializable;

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
public class ParticipantRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8263861757549308557L;
	String fullName;
	String workCode;
}
