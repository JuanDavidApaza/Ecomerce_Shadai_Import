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

import edu.pe.idat.app.models.entity.Cliente;
import edu.pe.idat.app.models.entity.Producto;
import edu.pe.idat.app.models.entity.Venta;
import edu.pe.idat.app.models.entity.detalleVenta;
import edu.pe.idat.app.models.service.IServiceApp;

@Controller
@RequestMapping("venta")
@SessionAttributes("venta")
public class VentaController {

	@Autowired
	private IServiceApp iServiceApp;
	
	@GetMapping("/listarventas")
	public String listarventas(Model model,@RequestParam(defaultValue = "")String filtroventa) {
		model.addAttribute("ventas",iServiceApp.listarventas(filtroventa));
		model.addAttribute("filtroventa", filtroventa);
		return "listaventas";
	}
	
	@GetMapping("/detalleventa/{id}")
	public String listarUno(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		Venta venta = iServiceApp.buscarVentaPorId(id);
		if (venta == null) {
			redirectAttributes.addFlashAttribute("error", "�Compra no existe!");
			return "redirect:/cliente/listarcliente";
		}
		model.addAttribute("titulo", "Detalle de Venta: N� " + venta.getId());
		model.addAttribute("venta", venta);
		return "/detalleventa";
	}
	
	@GetMapping("/formularioventas/{cliente_id}")
	public String form(@PathVariable Integer cliente_id, Model model) {
		Cliente cliente = iServiceApp.buscarCliente(cliente_id);
		if (cliente == null) {
			return "redirect:/listarcliente";
		}
		Venta venta = new Venta();
		venta.setCliente(cliente);
		model.addAttribute("titulo", "Registrar Venta");
		model.addAttribute("venta", venta);
		return "formularioventas";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return iServiceApp.buscarProductoPorNombre(term);
	}
	 
	@GetMapping("/eliminar/{id}")
	public String eliminarVenta(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		iServiceApp.eliminarventa(id);
		redirectAttributes.addFlashAttribute("alert","Registro de venta eliminado exitosamente");
		return "redirect:/listarcliente";
	}
	
	

	@PostMapping("/formularioventas")
	public String registrarCliente(@Valid Venta venta, BindingResult bindingResult,
			@RequestParam(name = "producto_id[]", required = false) Integer[] producto_id,
	        @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
	        Model model, RedirectAttributes redirectAttributes, SessionStatus sessionStatus){
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("titulo", "Registrar Venta");
			return "/formularioventas";
		}
		
		if(producto_id == null || producto_id.length <= 0) {
			model.addAttribute("titulo", "Registrar Venta");
			model.addAttribute("alert","Debe seleccionar al menos un producto");
			return "/formularioventas";
		}
		
		for(int i = 0; i < producto_id.length; i++) {
			Producto producto = iServiceApp.buscarProducto(producto_id[i]);
			detalleVenta detalleVenta = new detalleVenta();
			detalleVenta.setProducto(producto);
			detalleVenta.setCantidad(cantidad[i]);
			venta.setItem(detalleVenta);
		}
		
		iServiceApp.guardarVenta(venta);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("info","Venta Guardada Exitosamente");
		
		return "redirect:/detallecliente/" + venta.getCliente().getId();
	}
}
