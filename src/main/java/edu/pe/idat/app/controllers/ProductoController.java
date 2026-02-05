package edu.pe.idat.app.controllers;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import edu.pe.idat.app.models.entity.Producto;
import edu.pe.idat.app.models.service.IServiceApp;
import edu.pe.idat.app.models.service.UplodadFileService;

@Controller
@SessionAttributes("producto")
public class ProductoController {
	private final Logger log = LoggerFactory.getLogger(ProductoController.class);
	

	@Autowired
	private IServiceApp iServiceApp;
	
	@Autowired
	private UplodadFileService upload;
	
	@GetMapping("/listarproducto")
	public String listarproducto(Model model, @RequestParam(defaultValue = "") String filtroProducto) {
		model.addAttribute("productos", iServiceApp.listarProducto(filtroProducto));
		model.addAttribute("filtroProducto", filtroProducto);
		return "listarproducto";
	}
	
	@GetMapping("/formularioproducto")
	public String formularioproducto(Model model) {
		Producto producto = new Producto();
		model.addAttribute("producto", producto);
		return "formularioproducto";
	}

	@PostMapping("/formularioproducto")
    public String registrarProducto(@Valid Producto producto, BindingResult bindingResult, @RequestParam("file") MultipartFile file,
            SessionStatus sessionStatus, RedirectAttributes redirectAttributes) throws IOException{
        if (bindingResult.hasErrors()) {
            return "formularioproducto";
        }
        // imagen
        if (producto.getId()==null) { //Cuando se crea un producto
            String nombreImagen = upload.guardarImagen(file);
            producto.setImagen(nombreImagen);
        } else {
            if (file.isEmpty()) { //Cuando editamos el producto pero no cambiamos la imagen.
                Producto p = new Producto();
                p = iServiceApp.buscarProducto(producto.getId());
                producto.setImagen(p.getImagen());
            } else { // Cuando se edita la imagen
                Producto p = new Producto();
                p = iServiceApp.buscarProducto(producto.getId());
                //Eliminar cuando no sea la imagen por defecto
                if (p.getImagen().equals("default.jpg")) { upload.eliminarImagen(p.getImagen()); }
                String nombreImagen = upload.guardarImagen(file);
                producto.setImagen(nombreImagen);
            }
        }
        iServiceApp.registrarProducto(producto);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("info","Producto Guardado Exitosamente");
        return "redirect:listarproducto";
    }
	@GetMapping("/formularioproducto/{id}")
	public String editarProducto(@PathVariable Integer id, Model model) {
		Producto producto = null;
		if(id > 0) {
			producto = iServiceApp.buscarProducto(id);
		} else {
			return "redirect:listarproducto";
		}
		model.addAttribute("producto", producto);
		return "formularioproducto";
	}
	
	@GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Producto p = new Producto();
        p = iServiceApp.buscarProducto(id);
        if (p.getImagen().equals("default.jpg")) { //Eliminar cuando no sea la imagen por defecto
            upload.eliminarImagen(p.getImagen()); }

        iServiceApp.eliminarProducto(id);
        redirectAttributes.addFlashAttribute("alert","Producto Eliminado Exitosamente");
        return "redirect:/listarproducto";
    }
	@PostMapping("/buscar")
	public String buscarProducto(@RequestParam String buscador, Model model) {
		log.info("Nombre del producto:{}",buscador);
		List<Producto> productos = iServiceApp.findAll().stream().filter(p -> p.getNombre().contains(buscador)).collect(Collectors.toList());
		model.addAttribute("productos",productos);
		
		return "Front/catalogo";
	}
	
}





