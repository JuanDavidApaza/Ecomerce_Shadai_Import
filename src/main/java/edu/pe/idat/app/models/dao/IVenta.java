package edu.pe.idat.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import edu.pe.idat.app.models.entity.Venta;

public interface IVenta extends CrudRepository<Venta, Integer>{

	@Query("select v from Venta v where v.descripcion like %?1%")
	public List<Venta> buscarPorDescripcion(String term);
}


