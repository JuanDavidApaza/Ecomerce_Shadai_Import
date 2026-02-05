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
@Table(name = "ventas")
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	@Column(nullable = false)
	private String descripcion;
	@Temporal(TemporalType.DATE)
	private Date fechacreacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clientes_id", nullable = false)
	private Cliente cliente;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "venta_id", nullable = false)
	private List<detalleVenta> items;
	
	@PrePersist
	public void prePersist() {
		fechacreacion = new Date();
	}
	
	public Venta() {
		items = new ArrayList<>();
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<detalleVenta> getItems() {
		return items;
	}

	public void setItems(List<detalleVenta> items) {
		this.items = items;
	}
	
	public void setItem(detalleVenta item) {
		items.add (item);
	}
	
	public Double calcularTotal() {
		Double total = 0.0;
		for (detalleVenta item : items) {
			total += item.calcularSubtotal();
		}
		return total;
	}
	
}	
