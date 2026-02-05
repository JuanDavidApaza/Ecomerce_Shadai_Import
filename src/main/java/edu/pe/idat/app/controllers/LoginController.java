package edu.pe.idat.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, 
			@RequestParam(required = false) String logout,
			Principal principal, Model model, RedirectAttributes redirectAttributes) {
		if (principal != null) {
			redirectAttributes.addFlashAttribute("error", "¡Ya iniciaste sesión!");
			return "redirect:/home";
		}
		if (error != null) {
			model.addAttribute("error", "¡Usuario o Clave incorrectos!");
		}
		if (logout != null) {
			model.addAttribute("info", "¡Sesión terminada!");
		}
		return "login";
	}

}
