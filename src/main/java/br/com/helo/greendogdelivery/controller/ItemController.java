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

import br.com.helo.greendogdelivery.entities.Item;
import br.com.helo.greendogdelivery.repository.ItemRepository;

@Controller
@RequestMapping("/itens")
public class ItemController {

	private final ItemRepository itemRepository;
	private final String ITEM_URI = "itens/";

	public ItemController(final ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@GetMapping("/")
	public ModelAndView list() {
		final Iterable<Item> itens = this.itemRepository.findAll();
		return new ModelAndView(this.ITEM_URI + "list", "itens", itens);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") final Item item) {
		return new ModelAndView(this.ITEM_URI + "view", "item", item);
	}

	@GetMapping("/novo")
	public String createForm(@ModelAttribute final Item item) {
		return this.ITEM_URI + "form";
	}

	@PostMapping(params = "form")
	public ModelAndView create(@Valid Item item, final BindingResult result, final RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView(this.ITEM_URI + "form", "formErrors", result.getAllErrors());
		}
		item = this.itemRepository.save(item);
		redirect.addFlashAttribute("globalMessage", "Item gravado com sucesso");
		return new ModelAndView("redirect:/" + this.ITEM_URI + "{item.id}", "item.id", item.getId());
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") final Long id, final RedirectAttributes redirect) {
		this.itemRepository.delete(id);
		final Iterable<Item> itens = this.itemRepository.findAll();

		final ModelAndView mv = new ModelAndView(this.ITEM_URI + "list", "itens", itens);
		mv.addObject("globalMessage", "Item removido com sucesso");

		return mv;
	}

	@GetMapping(value = "alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") final Item item) {
		return new ModelAndView(this.ITEM_URI + "form", "item", item);
	}

}
