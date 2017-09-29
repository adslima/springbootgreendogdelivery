/**
 * Cielo S.A. Projeto Convivência
 *
 * springbootgreendogdelivery br.com.helo.greendogdelivery.repository
 *
 * Copyright 2017
 */
package br.com.helo.greendogdelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.helo.greendogdelivery.entities.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}