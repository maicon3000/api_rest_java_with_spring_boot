package br.com.maicon.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maicon.model.Profissionais;
import br.com.maicon.repositories.ProfissionaisRepository;
import br.com.maicon.util.ApiResponse;

@Service
public class ProfissionaisService {

	@Autowired
	private ProfissionaisRepository profissionaisRepository;

	private Logger logger = Logger.getLogger(ProfissionaisService.class.getName());

	public List<Profissionais> findAll() {
		logger.info("Finding all professionals");

		List<Profissionais> profissionais = new ArrayList<>();
		profissionais = profissionaisRepository.findAll();

		if (profissionais.isEmpty()) {
			throw new RuntimeException("Nenhum profissional encontrado");
		}

		return profissionais;
	}

	public Profissionais findById(Long id) {
		logger.info("Finding professional with ID " + id);
		return profissionaisRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
	}

	public Profissionais create(Profissionais profissional) {
		logger.info("Creating one professional");

		Profissionais savedProfissional = profissionaisRepository.save(profissional);

		return savedProfissional;
	}

	public Profissionais update(Profissionais profissional) {
		if(profissionaisRepository.existsById(profissional.getId())) {
			logger.info("Updating professional with ID " + profissional.getId());
			Profissionais updateProfissional = profissionaisRepository.save(profissional);
			return updateProfissional;
		} else {
			throw new RuntimeException("Profissional não encontrado para atualização");
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public ApiResponse delete(Long id) {
	    if (profissionaisRepository.existsById(id)) {
	    	logger.info("Deleting professional with ID " + id);
	        profissionaisRepository.deleteById(id);
	        return new ApiResponse(true, "Profissional deletado com sucesso!");
	    } else {
	    	logger.info("Professional with ID " + id + " not found");
	        return new ApiResponse(false, "Profissional com ID " + id + " não encontrado.");
	    }
	}
}
