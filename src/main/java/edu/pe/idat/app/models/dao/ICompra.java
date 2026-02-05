package edu.pe.idat.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.pe.idat.app.models.entity.Compra;
public interface ICompra extends CrudRepository<Compra, Integer>{

	@Query("select c from Compra c where c.descripcion like %?1%")
	public List<Compra> buscarcompradescrip(String term);
}
