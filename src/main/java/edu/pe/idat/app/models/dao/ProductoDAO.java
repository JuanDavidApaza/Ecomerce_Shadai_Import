/*package edu.pe.idat.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.pe.idat.app.models.entity.Producto;

@Repository
public class ProductoDAO implements IProducto {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> listarProducto() {
		return entityManager.createQuery("from Producto", Producto.class).getResultList();
	}

	@Override
	@Transactional
	public void registrarProducto(Producto producto) {
		if(producto.getId() != null && producto.getId() > 0) {
			entityManager.merge(producto);
		} else {
			entityManager.persist(producto);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto buscarProducto(Integer id) {
		return entityManager.find(Producto.class, id);
	}
	
	@Override
	@Transactional
	public void eliminarProducto(Integer id) {
		Producto producto = buscarProducto(id);
		if (producto != null) {
		entityManager.remove(producto);
		}
		
	}

}





*/