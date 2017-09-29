/**
 * Cielo S.A. Projeto Convivência
 *
 * springbootgreendogdelivery br.com.helo.greendogdelivery.repository
 *
 * Copyright 2017
 */
package br.com.helo.greendogdelivery.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.helo.greendogdelivery.entities.Cliente;
import br.com.helo.greendogdelivery.entities.Item;
import br.com.helo.greendogdelivery.entities.Pedido;

/**
 *
 * @author <a href="mailto:andrews.silva@accenture.com">andrews.silva</a>
 * @date 28 de set de 2017 11:21:20
 */
@Component
public class RepositoryTest implements ApplicationRunner {

	private static final long ID_CLIENTE_FERNANDO = 11l;
	private static final long ID_CLIENTE_ZE_PEQUENO = 22l;

	private static final long ID_ITEM1 = 100l;
	private static final long ID_ITEM2 = 101l;
	private static final long ID_ITEM3 = 102l;

	private static final long ID_PEDIDO1 = 1000l;
	private static final long ID_PEDIDO2 = 1001l;
	private static final long ID_PEDIDO3 = 1002l;

	/**
	 *
	 */
	@Autowired
	private ClienteRepository clienteRepository;

	/* (non-Javadoc)
	 *
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments) */
	@Override
	public void run(final ApplicationArguments applicationArguments) throws Exception {

		System.out.println(">>> Iniciando carga de dados...");

		final Cliente fernando = new Cliente(ID_CLIENTE_FERNANDO, "Fernando Boaglio", "Sampa");
		final Cliente zePequeno = new Cliente(ID_CLIENTE_ZE_PEQUENO, "Zé Pequeno", "Cidade de Deus");

		final Item dog1 = new Item(ID_ITEM1, "Green Dog tradicional", 25d);
		final Item dog2 = new Item(ID_ITEM2, "Green Dog tradicional picante", 27d);
		final Item dog3 = new Item(ID_ITEM3, "Green Dog max salada", 30d);

		final List<Item> listaPedidoFernando1 = new ArrayList<Item>();
		listaPedidoFernando1.add(dog1);

		final List<Item> listaPedidoZePequeno1 = new ArrayList<Item>();
		listaPedidoZePequeno1.add(dog2);
		listaPedidoZePequeno1.add(dog3);

		final Pedido pedidoDoFernando = new Pedido(ID_PEDIDO1, fernando, listaPedidoFernando1, dog1.getPreco());
		fernando.novoPedido(pedidoDoFernando);

		final Pedido pedidoDoZepequeno = new Pedido(ID_PEDIDO2, zePequeno, listaPedidoZePequeno1,
				dog2.getPreco() + dog3.getPreco());
		zePequeno.novoPedido(pedidoDoZepequeno);

		System.out.println(">>> Pedido 1 - Fernando : " + pedidoDoFernando);
		System.out.println(">>> Pedido 2 - Ze Pequeno: " + pedidoDoZepequeno);

		this.clienteRepository.saveAndFlush(zePequeno);

		System.out.println(">>> Gravado cliente 2: " + zePequeno);

		final List<Item> listaPedidoFernando2 = new ArrayList<Item>();
		listaPedidoFernando2.add(dog2);

		final Pedido pedido2DoFernando = new Pedido(ID_PEDIDO3, fernando, listaPedidoFernando2, dog2.getPreco());
		fernando.novoPedido(pedido2DoFernando);

		this.clienteRepository.saveAndFlush(fernando);

		System.out.println(">>> Pedido 2 - Fernando : " + pedido2DoFernando);
		System.out.println(">>> Gravado cliente 1: " + fernando);

	}

}