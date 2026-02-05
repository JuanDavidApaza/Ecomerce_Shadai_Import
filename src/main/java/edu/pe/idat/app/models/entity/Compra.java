package edu.pe.idat.app.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "compras")
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	@Column(nullable = false)
	private String descripcion;
	@Temporal(TemporalType.DATE)
	private Date fechacreacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proveedor_id", nullable = false)
	private Proveedor proveedor;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "compra_id", nullable = false)
	private List<DetalleCompra> elements;
	
	@PrePersist
	public void prePersist() {
		fechacreacion = new Date();
	}
	public Compra() {
		elements = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<DetalleCompra> getElements() {
		return elements;
	}

	public void setElements(List<DetalleCompra> elements) {
		this.elements = elements;
	}
	
	public void setElement(DetalleCompra element) {
		elements.add(element);
	}
	
	public Double calcularTotal() {
		Double total = 0.0;
		for (DetalleCompra element : elements) {
			total += element.calcularSubtotal();
		}
		return total;
	}
	
	
}
