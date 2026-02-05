package edu.pe.idat.app.models.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.pe.idat.app.models.entity.Cliente;

public interface ICliente extends CrudRepository<Cliente, Integer>{

	@Query("select c from Cliente c where c.nombre like %?1%"
			+ "OR c.apellidos like %?1%"
			+ "OR c.dni like %?1%")
	public List<Cliente> buscarPorNombre(String term);
}

