package edu.pe.idat.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.pe.idat.app.models.entity.Producto;

public interface IProducto extends CrudRepository<Producto, Integer> {

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> buscarPorNombre(String term);
}
