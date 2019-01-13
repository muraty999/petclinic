package com.javaegitimleri.petclinic.web;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.javaegitimleri.petclinic.model.Owner;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@ActiveProfiles("dev")
public class PetClinicTestControllerTests {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Before
	public void Setup() {
		restTemplate = restTemplate.withBasicAuth("user2", "secret");
	}
	
	@Test
	public void deleteOwner() {
		restTemplate.delete("http://localhost:8080/rest/owner/1");

		try {
			restTemplate.delete("http://localhost:8080/rest/owner/1");
			Assert.fail("should have not come here");
		} catch (HttpClientErrorException hex) {
			MatcherAssert.assertThat(hex.getStatusCode().value(), Matchers.equalTo(404));
			
		}
	}
	
	@Test
	public void updateOwner() {
		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class);
		
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Yusuf Eymen"));
		
		owner.setFirstName("Yusufcuk");
		
		restTemplate.put("http://localhost:8080/rest/owner/4", owner);
		
		owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class);
		
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Yusufcuk"));	
	}
	
	@Test
	public void createOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Mehmet Ali");
		owner.setLastName("Yilmaz");
		
		URI uri = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);
		
		Owner owner2 = restTemplate.getForObject(uri, Owner.class);
		
		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo("Mehmet Ali"));
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo("Yilmaz"));
	}
	
	
	@Test
	public void testGetOwnerById() {
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		//MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Kenan"));
	}
	
	@Test
	public void testGetOwnersByLastName() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Yilmaz", List.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String,String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());
		
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Murat","Yusuf Eymen"));
	}
	
	@Test
	public void testGetOwners() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String,String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());
		
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Murat","Yusuf Eymen","Kenan","Murat"));
	
	}
	
}
