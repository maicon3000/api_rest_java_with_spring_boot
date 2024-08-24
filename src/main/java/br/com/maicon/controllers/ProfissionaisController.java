package br.com.maicon.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.maicon.model.Profissionais;
import br.com.maicon.services.ProfissionaisService;
import lombok.Data;

@Data
@RestController
@RequestMapping("profissionais")
public class ProfissionaisController {

	private final ProfissionaisService service;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Profissionais findById(@PathVariable String id) {
	    Long longId = Long.parseLong(id);
	    return service.findById(longId);
	}

}
