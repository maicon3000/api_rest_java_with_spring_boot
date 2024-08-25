package br.com.maicon.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.maicon.model.Profissionais;
import br.com.maicon.services.ProfissionaisService;
import br.com.maicon.util.ApiResponse;
import lombok.Data;

@Data
@RestController
@RequestMapping("profissionais")
public class ProfissionaisController {

	private final ProfissionaisService service;
	
	/**
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profissionais> findAll() {
	    return service.findAll();
	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Profissionais findById(@PathVariable Long id) {
	    return service.findById(id);
	}
	
	/**
	 * @param profissional
	 * @return
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Profissionais create(@RequestBody Profissionais profissional) {
	    return service.create(profissional);
	}
	
	/**
	 * @param id
	 * @param profissional
	 * @return
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Profissionais update(@PathVariable Long id, @RequestBody Profissionais profissional) {
	    profissional.setId(id);
	    return service.update(profissional);
	}
	
	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
	    ApiResponse response = service.delete(id);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

}
