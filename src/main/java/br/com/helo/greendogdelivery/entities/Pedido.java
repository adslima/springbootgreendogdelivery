/**
 * Cielo S.A. Projeto Convivência
 *
 * springbootgreendogdelivery br.com.helo.greendogdelivery
 *
 * Copyright 2017
 */
package br.com.helo.greendogdelivery.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author <a href="mailto:andrews.silva@accenture.com">andrews.silva</a>
 * @date 28 de set de 2017 10:25:19
 */
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = true)
	private Cliente cliente;

	@ManyToMany
	@Cascade(CascadeType.MERGE)
	private List<Item> itens;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date data;

	@Min(1)
	private Double valorTotal;

	public Pedido() {
	}

	public Pedido(final Long id, final Cliente cliente, final List<Item> itens, final Double valorTotal) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.itens = itens;
		this.data = new Date();
		this.valorTotal = valorTotal;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Item> getItens() {
		return this.itens;
	}

	public void setItens(final List<Item> itens) {
		this.itens = itens;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(final Date data) {
		this.data = data;
	}

	public Double getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(final Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	/* (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode() */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Pedido other = (Pedido) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 *
	 * @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return "Pedido [id=" + this.id + ", cliente=" + this.cliente + ", itens=" + this.itens + ", data=" + this.data
				+ ", valorTotal=" + this.valorTotal + "]";
	}

}
