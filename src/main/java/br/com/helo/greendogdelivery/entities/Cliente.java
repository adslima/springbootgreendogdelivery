/**
 * Cielo S.A. Projeto Convivência
 *
 * springbootgreendogdelivery br.com.helo.greendogdelivery
 *
 * Copyright 2017
 */
package br.com.helo.greendogdelivery.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Length;

/**
 * @author <a href="mailto:andrews.silva@accenture.com">andrews.silva</a>
 * @date 28 de set de 2017 10:28:03
 */
@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Length(min = 2, max = 30, message = "O tamanho do nome deve ser entre {min} e {max} caracteres")
	private String nome;

	@NotNull
	@Length(min = 2, max = 300, message = "O tamanho do endereço deve ser entre {min} e {max} caracteres")
	private String endereco;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<Pedido> pedidos;

	/**
	 *
	 * Construtor
	 */
	public Cliente() {
	}

	/**
	 *
	 * Construtor
	 *
	 * @param id
	 * @param nome
	 * @param endereco
	 */
	public Cliente(final Long id, final String nome, final String endereco) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
	}

	/**
	 * @return retorna o valor de {@link #id}
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * seta o valor do parametro id no atributo {@link #id}
	 *
	 * @param id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return retorna o valor de {@link #nome}
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * seta o valor do parametro nome no atributo {@link #nome}
	 *
	 * @param nome
	 */
	public void setNome(final String nome) {
		this.nome = nome;
	}

	/**
	 * @return retorna o valor de {@link #endereco}
	 */
	public String getEndereco() {
		return this.endereco;
	}

	/**
	 * seta o valor do parametro endereco no atributo {@link #endereco}
	 *
	 * @param endereco
	 */
	public void setEndereco(final String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return retorna o valor de {@link #pedidos}
	 */
	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	/**
	 * seta o valor do parametro pedidos no atributo {@link #pedidos}
	 *
	 * @param pedidos
	 */
	public void setPedidos(final List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	/**
	 *
	 * <code><pre></pre></code>
	 *
	 * @param pedido void
	 */
	public void novoPedido(final Pedido pedido) {

		if (this.pedidos == null) {
			this.pedidos = new ArrayList<Pedido>();
		}

		this.pedidos.add(pedido);

	}

	/* (non-Javadoc)
	 *
	 * @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return "Cliente [id=" + this.id + ", nome=" + this.nome + ", endereco=" + this.endereco + "]";
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
		final Cliente other = (Cliente) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
