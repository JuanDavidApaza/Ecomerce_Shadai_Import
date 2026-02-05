/*package edu.pe.idat.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.pe.idat.app.models.entity.Cliente;

@Repository
public class ClienteDAO implements ICliente {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> listarTodo() {
		return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
	}

	@Override
	@Transactional
	public void registrarCliente(Cliente cliente) {
		if(cliente.getId() != null && cliente.getId() > 0) {
			entityManager.merge(cliente);
		} else {
			entityManager.persist(cliente);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente buscarCliente(Integer id) {
		return entityManager.find(Cliente.class, id);
	}
	
	@Override
	@Transactional
	public void eliminarCliente(Integer id) {
		Cliente cliente = buscarCliente(id);
		if (cliente != null) {
		entityManager.remove(cliente);
		}
		
	}

}





*/