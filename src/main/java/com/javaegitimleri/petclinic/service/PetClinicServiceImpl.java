package com.javaegitimleri.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.dao.PetRepository;
import com.javaegitimleri.petclinic.dao.jpa.VetRepository;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.exception.VetNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.model.Vet;

@Service
@Transactional(rollbackFor=Exception.class)
public class PetClinicServiceImpl implements PetClinicService {

	@Autowired
	private JavaMailSender mailSender;
	
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	public void setOwnerRepository(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}
	
	@Autowired
	private VetRepository vetRepository;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	@Secured(value= {"ROLE_USER","ROLE_EDITOR"})
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public Owner findOwner(Long id) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(id);
		if (owner == null) {
			throw new OwnerNotFoundException("Owner is not found"); 
		}
		return owner;
	}

	@Override
	public void create(Owner owner) {
		ownerRepository.create(owner);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("k@s");
		msg.setTo("m@y");
		msg.setSubject("Owner created !");
		msg.setText("Owner entity with id: " + owner.getId() + " created successfully.");
		
		mailSender.send(msg);
	}

	@Override
	public void update(Owner owner) {
		ownerRepository.update(owner);
	}

	@Override
	public void delete(Long id) {
		petRepository.deleteByOwnerId(id);
		ownerRepository.delete(id);
		
		//if (false) throw new RuntimeException("rollback");
	}


	@Override
	public List<Vet> findVets() {
		return vetRepository.findAll();
	}


	@Override
	public Vet findVet(Long id) throws VetNotFoundException {
		return vetRepository.findById(id).orElseThrow(() -> {return new VetNotFoundException("Vet not found by id " + id); });
	}

}
