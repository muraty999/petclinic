package com.javaegitimleri.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef="petClinicAuditorAware")
@ServletComponentScan
@SpringBootApplication
@EnableConfigurationProperties(value=PetclinicProperties.class)
public class PetClinicApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(PetClinicApplication.class, args);

	}

}
