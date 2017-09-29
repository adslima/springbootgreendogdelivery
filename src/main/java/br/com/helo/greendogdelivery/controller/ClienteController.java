package br.com.helo.greendogdelivery.controller;

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
import br.com.helo.greendogdelivery.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	private final String CLIENTE_URI = "clientes/";

	public ClienteController(final ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/")
	public ModelAndView list() {
		final Iterable<Cliente> clientes = this.clienteRepository.findAll();
		return new ModelAndView(this.CLIENTE_URI + "list", "clientes", clientes);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") final Cliente cliente) {
		return new ModelAndView(this.CLIENTE_URI + "view", "cliente", cliente);
	}

	@GetMapping("/novo")
	public String createForm(@ModelAttribute final Cliente cliente) {
		return this.CLIENTE_URI + "form";
	}

	@PostMapping(params = "form")
	public ModelAndView create(@Valid Cliente cliente, final BindingResult result, final RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView(this.CLIENTE_URI + "form", "formErrors", result.getAllErrors());
		}
		cliente = this.clienteRepository.save(cliente);
		redirect.addFlashAttribute("globalMessage", "Cliente gravado com sucesso");
		return new ModelAndView("redirect:/" + this.CLIENTE_URI + "{cliente.id}", "cliente.id", cliente.getId());
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") final Long id, final RedirectAttributes redirect) {
		this.clienteRepository.delete(id);
		final Iterable<Cliente> clientes = this.clienteRepository.findAll();

		final ModelAndView mv = new ModelAndView(this.CLIENTE_URI + "list", "clientes", clientes);
		mv.addObject("globalMessage", "Cliente removido com sucesso");

		return mv;
	}

	@GetMapping(value = "alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") final Cliente cliente) {
		return new ModelAndView(this.CLIENTE_URI + "form", "cliente", cliente);
	}

}
