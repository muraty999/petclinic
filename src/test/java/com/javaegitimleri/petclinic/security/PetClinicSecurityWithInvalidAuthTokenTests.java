package com.javaegitimleri.petclinic.security;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties="spring.profiles.active=dev")
public class PetClinicSecurityWithInvalidAuthTokenTests {
	
	@Autowired
	private PetClinicService petClinicService; 
	
	@Before
	public void setup() {
		TestingAuthenticationToken auth = new TestingAuthenticationToken("someuser", "secret", "ROLE_USER");
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}
	
	@Test
	public void testFindOwners() {
		List<Owner> list = petClinicService.findOwners();
		MatcherAssert.assertThat(list.size(), Matchers.equalTo(10));
		
		
	}

}
