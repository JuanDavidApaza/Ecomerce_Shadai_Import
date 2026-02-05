package edu.pe.idat.app.models.service;

import java.util.List;
import java.util.Optional;

import edu.pe.idat.app.models.entity.Cliente;
import edu.pe.idat.app.models.entity.Compra;
import edu.pe.idat.app.models.entity.Producto;
import edu.pe.idat.app.models.entity.Proveedor;
import edu.pe.idat.app.models.entity.Venta;

public interface IServiceApp {
	
	//Acciones de la entidad Proveedorr
	public List<Proveedor> listarProveedores(String nombre);
	
	public Proveedor buscarProveedor(Integer id);

	public void registarProveedor(Proveedor proveedor);

	public void eliminarProveedor(Integer id);

	
	//Acciones de la Entidad Productos
	
	public List<Producto> listarProducto(String nombre);

	public void registrarProducto(Producto producto);
	
	public Producto buscarProducto(Integer id);
	
	public void eliminarProducto(Integer id);
	
	public List<Producto> buscarProductoPorNombre(String nombre);
	
	public List<Producto> findAll();
	
	public Optional<Producto>options(Integer id);
	
	
	//Acciones de la Entidad Clientes
	
	public List<Cliente> listarCliente(String nombre);

	public void registrarCliente(Cliente cliente);
	
	public Cliente buscarCliente(Integer id);
	
	public void eliminarCliente(Integer id);;
	
	//Acciones de la venta
	
	public List<Venta> listarventas(String descripcion);
	
	public void guardarVenta(Venta venta);

	public void eliminarventa(Integer id);

	public Venta buscarVentaPorId(Integer id);
	
	//Acciones de la Compra
	
	public List<Compra> listarcompras(String Descrip);

	public void guardarCompra(Compra compra);
	
	public void eliminarcompra(Integer id);
	
	public Compra buscarCompraPorId(Integer id);
}
