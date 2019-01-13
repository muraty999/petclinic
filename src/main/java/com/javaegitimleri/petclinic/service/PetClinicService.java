package com.javaegitimleri.petclinic.service;

import java.util.List;

import com.javaegitimleri.petclinic.exception.VetNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.model.Vet;

public interface PetClinicService {

	List<Owner> findOwners();
	List<Owner> findOwners(String lastName);
	Owner findOwner(Long id);
	void create(Owner owner);
	void update(Owner owner);
	void delete(Long id);
	
	List<Vet> findVets();
	Vet findVet(Long id) throws VetNotFoundException;
}
