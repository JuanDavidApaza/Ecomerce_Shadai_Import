package edu.pe.idat.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.pe.idat.app.models.entity.DetallePedido;
import edu.pe.idat.app.models.entity.Pedido;
import edu.pe.idat.app.models.entity.Producto;
import edu.pe.idat.app.models.service.IServiceApp;


@Controller
public class HomeController {
	
	private final Logger log= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IServiceApp iServiceApp;
	
	//Para guardar los pedidos 
		List<DetallePedido> pedidos= new ArrayList<DetallePedido>();
		
		//Descripcion del pedido
		Pedido pedido = new Pedido();

	@GetMapping("/home")
	public String hom() {
		return "Front/home";
	}
	
	@GetMapping("/")
	public String home() {
		return "Front/home";
	}
	
	@GetMapping("/catalogo")
	public String cat(Model model) {
		
		List<Producto> productos = iServiceApp.listarProducto(null);
		model.addAttribute("productos", productos);
		return "Front/catalogo";
	}
	
	@GetMapping("/contactenos")
	public String contacto() {
		return "Front/contactenos";
	}
	
	@GetMapping("/productodetalle/{id}")
	public String detalleproducto(@PathVariable Integer id,Model model) {
		log.info("Id producto  enviado como parametro{}",id);
		Producto producto = new Producto();
		Optional<Producto> productoOptional = iServiceApp.options(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto",producto);
		
		return "Front/productodetalle";
	}
	
	@PostMapping("/carrito")
	public String agregarcar(@RequestParam Integer id, @RequestParam Integer cantidad,
			Model model){
		DetallePedido detallePedido = new DetallePedido();
		Producto producto = new Producto();
		double sumatotal = 0;
		
		Optional<Producto> optionalproducto = iServiceApp.options(id);
		log.info("producto añadido: {}",optionalproducto.get());
		log.info("cantidad: {}", cantidad);
		producto = optionalproducto.get();
		
		detallePedido.setCantidad(cantidad);
		detallePedido.setPrecio(producto.getPrecioVenta());
		detallePedido.setNombre(producto.getNombre());
		detallePedido.setTotal(producto.getPrecioVenta()*cantidad);
		detallePedido.setProducto(producto);
		
		pedidos.add(detallePedido);
		
		sumatotal = pedidos.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		pedido.setTotal(sumatotal);
		model.addAttribute("carrito", pedidos);
		model.addAttribute("pedido", pedido);
		return "Front/carrito";
	}
	//Quitar Carrito de la lista Producto
	@GetMapping("delete/carrito/{id}")
	public String delte(@PathVariable Integer id, Model model) {
		
		List<DetallePedido> pedidoNuevo = new ArrayList<DetallePedido>();
		for(DetallePedido detallePedido : pedidos) {
			if(detallePedido.getProducto().getId()!=id) {
				pedidoNuevo.add(detallePedido);
			}
		}
		
		
		//nueva lista de Pedidos
		pedidos = pedidoNuevo;
		
		double sumatotal=0;
		sumatotal = pedidos.stream().mapToDouble(dt->dt.getTotal()).sum();
		
		pedido.setTotal(sumatotal);
		model.addAttribute("carrito", pedidos);
		model.addAttribute("pedido", pedido);
		
		return "Front/carrito";
	}

	@GetMapping("/registro")
	public String registrarse() {
		return "/registro";
	}
	
	@GetMapping("/getcarrito")
	public String  getcarrito(Model model) {
		model.addAttribute("carrito", pedidos);
		model.addAttribute("pedido", pedido);
		
		return "Front/carrito";
	}
}
	

