package edu.pe.idat.app.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.pe.idat.app.models.dao.ICliente;
import edu.pe.idat.app.models.dao.ICompra;
import edu.pe.idat.app.models.dao.IProducto;
import edu.pe.idat.app.models.dao.IProveedor;
import edu.pe.idat.app.models.dao.IVenta;
import edu.pe.idat.app.models.entity.Cliente;
import edu.pe.idat.app.models.entity.Compra;
import edu.pe.idat.app.models.entity.Producto;
import edu.pe.idat.app.models.entity.Proveedor;
import edu.pe.idat.app.models.entity.Venta;

@Service
public class ServiceApp implements IServiceApp{
   
	//Metodos para el Crud  de Proveedor 
	@Autowired
	private IProveedor iProveedor;
	@Autowired
	private IProducto iProducto;
	@Autowired
	private ICliente iCliente;
	@Autowired
	private IVenta iVenta;
	@Autowired
	private ICompra iCompra;
	
	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> listarProveedores(String nombre) {	
		if (nombre != null) {
            return iProveedor.buscarPorNombre(nombre);
        }
		return (List<Proveedor>) iProveedor.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Proveedor buscarProveedor(Integer id) {
		return iProveedor.findById(id).orElse(null);
	}

	@Override
	public void registarProveedor(Proveedor proveedor) {
		iProveedor.save(proveedor);
	}

	@Override
	public void eliminarProveedor(Integer id) {
		iProveedor.deleteById(id);
		
	}

	
	//Metodos para el Crud  de Prodcutos
	@Override
	public List<Producto> listarProducto(String nombre) {
        if (nombre != null) {
            return iProducto.buscarPorNombre(nombre);
        }
        return (List<Producto>) iProducto.findAll();
    }
	
	@Override
	public void registrarProducto(Producto producto) {
		iProducto.save(producto);
		
	}

	@Override
	public Producto buscarProducto(Integer id) {
		return iProducto.findById(id).orElse(null);
	}

	@Override
	public void eliminarProducto(Integer id) {
		iProducto.deleteById(id);
		
	}
	@Override
	public List<Producto> findAll() {
		return (List<Producto>) iProducto.findAll();
	}

	@Override
	public List<Producto> buscarProductoPorNombre(String nombre) {
		if (nombre != null) {
			return iProducto.buscarPorNombre(nombre);
		}
		return iProducto.buscarPorNombre(nombre);
	}
	
	@Override
	public Optional<Producto> options(Integer id) {
		return iProducto.findById(id);
	}
	
	
	//Metodos para el crud clientes 
	@Override
	public List<Cliente> listarCliente(String nombre) {
        if (nombre != null) {
            return iCliente.buscarPorNombre(nombre);
        }
        return (List<Cliente>) iCliente.findAll();
    }

	@Override
	public void registrarCliente(Cliente cliente) {
		iCliente.save(cliente);
		
	}

	@Override
	public Cliente buscarCliente(Integer id) {
		return iCliente.findById(id).orElse(null);
		
	}

	@Override
	public void eliminarCliente(Integer id) {
		iCliente.deleteById(id);
		
	}

	//Metodo para listar las ventas
	@Override
	public List<Venta> listarventas(String descripcion) {
		if (descripcion != null) {
			return iVenta.buscarPorDescripcion(descripcion);
		}
		return (List<Venta>) iVenta.findAll();
	}

	@Override
	@Transactional
	public void guardarVenta(Venta venta) {
		iVenta.save(venta);
	}	

	@Override
	public void eliminarventa(Integer id) {
		iVenta.deleteById(id);
		
	}
	
	@Override
	public Venta buscarVentaPorId(Integer id) {
		return iVenta.findById(id).orElse(null);
	}
	
	//Metodo para listar las compras
	@Override
	public List<Compra> listarcompras(String Descrip) {
		if (Descrip != null) {
			return (List<Compra>) iCompra.buscarcompradescrip(Descrip);
		}
		return (List<Compra>) iCompra.findAll();
	}
	
	@Override
	@Transactional
	public void guardarCompra(Compra compra) {
		iCompra.save(compra);
	}

	@Override
	public void eliminarcompra(Integer id) {
		iCompra.deleteById(id);
		
	}

	@Override
	public Compra buscarCompraPorId(Integer id) {
		return iCompra.findById(id).orElse(null);
	}
	
	

}
