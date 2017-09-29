package br.com.helo.greendogdelivery.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.helo.greendogdelivery.entities.Cliente;
import br.com.helo.greendogdelivery.entities.Item;
import br.com.helo.greendogdelivery.entities.Pedido;
import br.com.helo.greendogdelivery.repository.ClienteRepository;
import br.com.helo.greendogdelivery.repository.ItemRepository;
import br.com.helo.greendogdelivery.repository.PedidoRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoRepository pedidoRepository;
	private final ItemRepository itemRepository;
	private final ClienteRepository clienteRepository;
	private final String ITEM_URI = "pedidos/";

	public PedidoController(final PedidoRepository pedidoRepository, final ItemRepository itemRepository,
			final ClienteRepository clienteRepository) {
		this.pedidoRepository = pedidoRepository;
		this.itemRepository = itemRepository;
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/")
	public ModelAndView list() {
		final Iterable<Pedido> pedidos = this.pedidoRepository.findAll();
		return new ModelAndView(this.ITEM_URI + "list", "pedidos", pedidos);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") final Pedido pedido) {
		return new ModelAndView(this.ITEM_URI + "view", "pedido", pedido);
	}

	@GetMapping("/novo")
	public ModelAndView createForm(@ModelAttribute final Pedido pedido) {

		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("todosItens", this.itemRepository.findAll());
		model.put("todosClientes", this.clienteRepository.findAll());
		return new ModelAndView(this.ITEM_URI + "form", model);

	}

	@PostMapping(params = "form")
	public ModelAndView create(@Valid Pedido pedido, final BindingResult result, final RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView(this.ITEM_URI + "form", "formErrors", result.getAllErrors());
		}

		if (pedido.getId() != null) {
			final Pedido pedidoParaAlterar = this.pedidoRepository.findOne(pedido.getId());
			final Cliente c = this.clienteRepository.findOne(pedidoParaAlterar.getCliente().getId());
			pedidoParaAlterar.setItens(pedido.getItens());
			double valorTotal = 0;
			for (final Item i : pedido.getItens()) {
				valorTotal += i.getPreco();
			}
			pedidoParaAlterar.setData(pedido.getData());
			pedidoParaAlterar.setValorTotal(valorTotal);
			c.getPedidos().remove(pedidoParaAlterar.getId());
			c.getPedidos().add(pedidoParaAlterar);
			this.clienteRepository.save(c);
		} else {
			final Cliente c = this.clienteRepository.findOne(pedido.getCliente().getId());
			double valorTotal = 0;
			for (final Item i : pedido.getItens()) {
				valorTotal += i.getPreco();
			}
			pedido.setValorTotal(valorTotal);
			pedido = this.pedidoRepository.save(pedido);
			c.getPedidos().add(pedido);
			this.clienteRepository.save(c);
		}
		redirect.addFlashAttribute("globalMessage", "Pedido gravado com sucesso");
		return new ModelAndView("redirect:/" + this.ITEM_URI + "{pedido.id}", "pedido.id", pedido.getId());
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") final Long id, final RedirectAttributes redirect) {

		final Pedido pedidoParaRemover = this.pedidoRepository.findOne(id);

		final Cliente c = this.clienteRepository.findOne(pedidoParaRemover.getCliente().getId());
		c.getPedidos().remove(pedidoParaRemover);

		this.clienteRepository.save(c);
		this.pedidoRepository.delete(id);

		final Iterable<Pedido> pedidos = this.pedidoRepository.findAll();

		final ModelAndView mv = new ModelAndView(this.ITEM_URI + "list", "pedidos", pedidos);
		mv.addObject("globalMessage", "Pedido removido com sucesso");

		return mv;
	}

	@GetMapping(value = "alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") final Pedido pedido) {

		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("todosItens", this.itemRepository.findAll());
		model.put("todosClientes", this.clienteRepository.findAll());
		model.put("pedido", pedido);

		return new ModelAndView(this.ITEM_URI + "form", model);
	}

}
