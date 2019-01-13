package com.javaegitimleri.petclinic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetclinicConfiguration {
	
	@Autowired
	private PetclinicProperties petclinicProperties;
	
	//@Value("${spring.thymeleaf.enabled}")
	//private boolean thm;

	@PostConstruct
	public void init() {
		System.out.println("Display owner with owners: " + petclinicProperties.isDisplayOwnersWithPets());
		//System.out.println("Thymeleaf: " + thm);
	}
}
