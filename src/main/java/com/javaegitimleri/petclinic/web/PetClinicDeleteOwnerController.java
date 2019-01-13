package com.javaegitimleri.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

@Controller
public class PetClinicDeleteOwnerController {
	
	@Autowired
	private PetClinicService petClinicService;
	
	@GetMapping("/owners/delete/{id}")
	public String loadOwner(@PathVariable Long id, ModelMap modelMap) {
		Owner owner = petClinicService.findOwner(id);
		modelMap.put("owner", owner);
		return "deleteOwner";		
	}

	@PostMapping("/owners/delete/{id}")
	public String handleFormSubmit(@PathVariable Long id, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "deleteOwner";
		}
		petClinicService.delete(id);
		redirectAttributes.addFlashAttribute("message","Owner deleted with id: "+id);
		return "redirect:/owners";
	}
}
