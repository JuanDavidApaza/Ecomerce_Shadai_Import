/*                      package edu.pe.idat.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.pe.idat.app.models.entity.Proveedor;

@Repository
public class ProveedorDAO implements IProveedor {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> listarproveedores() {
		return entityManager.createQuery("from Proveedor", Proveedor.class).getResultList();
	}

	@Override
	@Transactional
	public void registarprovedor(Proveedor proveedor) {
		if(proveedor.getId()!= null && proveedor.getId()>0) {
			entityManager.merge(proveedor);
		}else {
			entityManager.persist(proveedor);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public Proveedor buscarproveedor(Integer id) {
		return entityManager.find(Proveedor.class, id);
	}

	@Override
	@Transactional
	public void eliminarproveedor(Integer id) {
		Proveedor proveedor = buscarproveedor(id);
		if(proveedor != null) {
			entityManager.remove(proveedor);
		}
		
	}

}
*/