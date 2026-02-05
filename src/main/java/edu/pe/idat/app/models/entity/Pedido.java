package edu.pe.idat.app.models.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String numero;
	private Date fechaCreacion;
	private Date fechaRecibida;

	private double total;
	
	@OneToMany(mappedBy = "pedido")
	private List<DetallePedido> pedido;
	
	public Pedido() {
	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaRecibida() {
		return fechaRecibida;
	}

	public void setFechaRecibida(Date fechaRecibida) {
		this.fechaRecibida = fechaRecibida;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
	public List<DetallePedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<DetallePedido> pedido) {
		this.pedido = pedido;
	}
	
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", numero=" + numero + ", fechaCreacion=" + fechaCreacion + ", fechaRecibida="
				+ fechaRecibida + ", total=" + total + "]";
	}

	
	
	
}
