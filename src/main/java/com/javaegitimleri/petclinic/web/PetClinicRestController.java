package com.javaegitimleri.petclinic.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaegitimleri.petclinic.exception.InternalServerException;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {
	
	@Autowired
	private PetClinicService clinicService;
	
	@DeleteMapping("/owner/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
		
		try {
			clinicService.delete(id);
			return ResponseEntity.ok().build();
		} catch (OwnerNotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalServerException(ex);
		}
	}
	
	@PutMapping("/owner/{id}")
	public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody Owner ownerRequest) {
		
		try {
			Owner owner = clinicService.findOwner(id);
			owner.setFirstName(ownerRequest.getFirstName());
			owner.setLastName(ownerRequest.getLastName());
			clinicService.update(owner);
			return ResponseEntity.ok().build();
		} catch (OwnerNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@PostMapping("/owner")
	public ResponseEntity<URI> createOwner(@RequestBody Owner owner) {
		try {
			clinicService.create(owner);
			Long id = owner.getId();
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(uri).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@GetMapping("/owners")
	public ResponseEntity<List<Owner>> getOwners() {
		//get all owners
		List<Owner> owners = clinicService.findOwners();
		return ResponseEntity.ok(owners);
	}
	
	@GetMapping("/owner")
	public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String lastName) {
		//get all owners with lastName
		List<Owner> owners = clinicService.findOwners(lastName);
		return ResponseEntity.ok(owners);
	}
	
	
	@GetMapping(value = "/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOwnerAsHateoasResource(@PathVariable("id") Long id) {
		try {

			Owner owner = clinicService.findOwner(id);
			ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(this.getClass());
			Link self = linkTo.slash("/owner/" + id).withSelfRel();
			Link create = linkTo.slash("/owner").withRel("create");
			Link update = linkTo.slash("/owner/" + id).withRel("update");
			Link delete = linkTo.slash("/owner/" + id).withRel("delete");
			Resource<Owner> resource = new Resource<Owner>(owner, self, create, update, delete); 
			return ResponseEntity. ok(resource);			
		} catch (OwnerNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}	
	}
	
	
	@GetMapping("/owner/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
		try {
			Owner owner = clinicService.findOwner(id);
			return ResponseEntity.ok(owner);			
		} catch (OwnerNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}		
	}
	
	

}
