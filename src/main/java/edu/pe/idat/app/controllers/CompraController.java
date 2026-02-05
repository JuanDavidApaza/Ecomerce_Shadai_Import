package edu.pe.idat.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.pe.idat.app.models.entity.Compra;
import edu.pe.idat.app.models.entity.DetalleCompra;
import edu.pe.idat.app.models.entity.Proveedor;
import edu.pe.idat.app.models.entity.Producto;
import edu.pe.idat.app.models.service.IServiceApp;

@Controller
@RequestMapping("compra")
@SessionAttributes("compra")
public class CompraController {

	@Autowired
	private IServiceApp iServiceApp;
	
	@GetMapping("/listarcompras")
	public String listarcompras(Model model,@RequestParam(defaultValue = "")String filtrocompra) {
		model.addAttribute("compras", iServiceApp.listarcompras(filtrocompra));
		model.addAttribute("filtrocompra", filtrocompra);
		return "listacompras";
	}

	@GetMapping("/detallecompra/{id}")
	public String listarUno(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		Compra compra = iServiceApp.buscarCompraPorId(id);
		if (compra == null) {
			redirectAttributes.addFlashAttribute("error", "�Compra no existe!");
			return "redirect:/proveedor/listaproveedor";
		}
		model.addAttribute("titulo", "Detalle de Compra: N� " + compra.getId());
		model.addAttribute("compra", compra);
		return "/detallecompra";
	}
	
	@GetMapping("/formulariocompras/{proveedor_id}")
	public String form(@PathVariable Integer proveedor_id, Model model) {
		Proveedor proveedor = iServiceApp.buscarProveedor(proveedor_id);
		if (proveedor == null) {
			return "redirect:/listaproveedor";
		}
		Compra compra = new Compra();
		compra.setProveedor(proveedor);
		model.addAttribute("titulo", "Registrar Compra");
		model.addAttribute("compra", compra);
		return "formulariocompras";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return iServiceApp.buscarProductoPorNombre(term);
	}
	
	@GetMapping("/eliminarcompra/{id}")
	public String eliminarCompra(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		iServiceApp.eliminarcompra(id);
		redirectAttributes.addFlashAttribute("alert","Registro de compra eliminado exitosamente");
		return "redirect:/listaproveedor";
	}
	
	
	@GetMapping("/eliminarcompralista/{id}")
	public String eliminarCompradeLista(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		iServiceApp.eliminarcompra(id);
		redirectAttributes.addFlashAttribute("alert","Registro de compra eliminado exitosamente");
		return "redirect:/compra/listacompras";
	}

	@PostMapping("/formulariocompras")
	public String registrarproveedor(@Valid Compra compra, BindingResult bindingResult,
			@RequestParam(name = "producto_id[]", required = false) Integer[] producto_id,
	        @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
	        Model model, RedirectAttributes redirectAttributes, SessionStatus sessionStatus){
	
		if (bindingResult.hasErrors()) {
			model.addAttribute("titulo", "Registrar Compra");
			return "/formulariocompras";
		}
		
		if(producto_id == null || producto_id.length <= 0) {
			model.addAttribute("titulo", "Registrar Venta");
			model.addAttribute("alert","Debe seleccionar al menos un producto");
			return "/formulariocompras";
		}
		
		for(int i = 0; i < producto_id.length; i++) {
			Producto producto = iServiceApp.buscarProducto(producto_id[i]);
			DetalleCompra DetalleCompra = new DetalleCompra();
			DetalleCompra.setProducto(producto);
			DetalleCompra.setCantidad(cantidad[i]);
			compra.setElement(DetalleCompra);
		}
		
		iServiceApp.guardarCompra(compra);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("info","Compra Guardada Exitosamente");
		
		return "redirect:/detalleproveedor/" + compra.getProveedor().getId();
		
	}

}
