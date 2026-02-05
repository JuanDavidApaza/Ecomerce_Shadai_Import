package edu.pe.idat.app.models.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "proveedores")
public class Proveedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	@Column(length = 30, nullable = false)
	private String nombres;
	@NotEmpty
	@Column(length = 30, nullable = false)
	private String apellido;
	@NotNull
	@Column(length = 8, nullable = false)
	private Integer dni;
	@NotNull
	private Integer telefono;
	@NotEmpty
	@Column(length = 30, nullable = false)
	private String empresa;
	@NotNull
	@Column(length = 11, nullable = false)
	private Integer ruc;
	@OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Compra> compras;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Integer getDni() {
		return dni;
	}
	public void setDni(Integer dni) {
		this.dni = dni;
	}
	public Integer getTelefono() {
		return telefono;
	}
	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}
	
	
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public Integer getRuc() {
		return ruc;
	}
	public void setRuc(Integer ruc) {
		this.ruc = ruc;
	}
	public List<Compra> getCompras() {
		return compras;
	}
	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	public void setCompra(Compra compra) {
		compras.add(compra);
	}
	
}
