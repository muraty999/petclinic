package com.javaegitimleri.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

@Controller
public class PetClinicEditOwnerController {
	
	@Autowired
	private PetClinicService service;
	
	@GetMapping("/owners/update/{id}")
	public String loadOwner(@PathVariable Long id, ModelMap modelMap) {
		Owner owner = service.findOwner(id);
		modelMap.put("owner", owner);
		return "editOwner";
	}
	
	@PostMapping("/owners/update/{id}")
	public String handleFormSubmit(@ModelAttribute @Valid Owner owner, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "editOwner";
		}
		service.update(owner);
		redirectAttributes.addFlashAttribute("message","Owner updated with id: "+owner.getId());
		return "redirect:/owners";
	}

}
