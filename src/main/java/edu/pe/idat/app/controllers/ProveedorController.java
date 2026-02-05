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
import edu.pe.idat.app.models.entity.Proveedor;
import edu.pe.idat.app.models.service.IServiceApp;

@Controller
@SessionAttributes("proveedor")
public class ProveedorController {

	@Autowired
	private IServiceApp iServiceApp;
	
	@GetMapping("/listaproveedor")
	public String listarPro(Model model, @RequestParam(defaultValue = "") String filtroProveedor) {
		model.addAttribute("proveedores", iServiceApp.listarProveedores(filtroProveedor));
		model.addAttribute("filtroProveedor", filtroProveedor);
		return "listaproveedor";
	}
	
	@GetMapping("/detalleproveedor/{id}")
	public String detalleCompra(@PathVariable Integer id, Model model,RedirectAttributes redirectAttributes) {
		Proveedor proveedor = iServiceApp.buscarProveedor(id);
		if (proveedor == null) {
			redirectAttributes.addFlashAttribute("error","Proveedor no existe");
			return "redirect:/listarproveedor";
		}
		model.addAttribute("titulo","Proveedor: " + proveedor.getNombres()+ " " + proveedor.getApellido());
		model.addAttribute("proveedor", proveedor);
		return "detalleproveedor";
	}
	
	@GetMapping("/formproveedor")
	public String formPro(Model model) {
		Proveedor proveedor = new Proveedor();
		model.addAttribute("proveedor",proveedor);
		return "formproveedor";
	}
	
	@PostMapping("/formproveedor")
	public String registrarproveedor(@Valid Proveedor proveedor,BindingResult bindingResult,SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "formproveedor";
		}
		
		iServiceApp.registarProveedor(proveedor);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("info","Proveedor Guardado Exitosamente");
		return "redirect:listaproveedor";
	}
	
	@GetMapping("/formproveedor/{id}")
	public String editarproveedor(@PathVariable Integer id,Model model) {
		Proveedor proveedor = null;
		if (id > 0) {
			proveedor = iServiceApp.buscarProveedor(id);
		}else {
			return "redirect:listaproveedor";
		}
		model.addAttribute("proveedor",proveedor);
		return "formproveedor";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarProveedor(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		iServiceApp.eliminarProveedor(id);
		redirectAttributes.addFlashAttribute("alert","Proveedor Eliminado Exitosamente");
		return "redirect:/listaproveedor";
	}
}

