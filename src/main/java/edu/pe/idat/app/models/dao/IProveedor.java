package edu.pe.idat.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.pe.idat.app.models.entity.Proveedor;


public interface IProveedor extends CrudRepository<Proveedor, Integer> {  

	@Query("select e from Proveedor e where e.nombres like %?1%"
			+ "OR e.apellido like %?1%"
			+ "OR e.dni like %?1%"
			+ "OR e.empresa like %?1%")
	public List<Proveedor> buscarPorNombre(String term);
}
