package edu.pe.idat.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.pe.idat.app.models.entity.Cliente;
import edu.pe.idat.app.models.service.IServiceApp;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IServiceApp iServiceApp;
	
	@GetMapping("/listarcliente")
	public String listarcliente(Model model, @RequestParam(defaultValue = "") String filtroCliente) {
		model.addAttribute("clientes", iServiceApp.listarCliente(filtroCliente));
		model.addAttribute("filtroProducto", filtroCliente);
		return "listarcliente";
	}
	
	@GetMapping("/detallecliente/{id}")
	public String detalleVenta(@PathVariable Integer id, Model model,RedirectAttributes redirectAttributes) {
		Cliente cliente = iServiceApp.buscarCliente(id);
		if (cliente == null) {
			redirectAttributes.addFlashAttribute("error","El Cliente no existe");
			return "redirect:/listarcliente";
		}
		model.addAttribute("titulo","Cliente: " + cliente.getNombre()+ " " + cliente.getApellidos());
		model.addAttribute("cliente", cliente);
		return "detallecliente";
	}
	
	@GetMapping("/formulariocliente")
	public String formulariocliente(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		return "formulariocliente";
	}

	@PostMapping("/formulariocliente")
	public String registrarCliente(@Valid Cliente cliente, BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "formulariocliente";
		}
		
		iServiceApp.registrarCliente(cliente);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("info","Cliente Guardado Exitosamente");
		return "redirect:listarcliente";
	}
	
	
	@GetMapping("/formulariocliente/{id}")
	public String editarCliente(@PathVariable Integer id, Model model) {
		Cliente cliente = null;
		if(id > 0) {
			cliente = iServiceApp.buscarCliente(id);
		} else {
			return "redirect:listarcliente";
		}
		
		model.addAttribute("cliente", cliente);
		return "formulariocliente";
	}
	
	@GetMapping("/eliminarCliente/{id}")
	public String eliminarCliente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		iServiceApp.eliminarCliente(id);
		redirectAttributes.addFlashAttribute("alert","Cliente Eliminado Exitosamente");
		return "redirect:/listarcliente";
	}
	
}





