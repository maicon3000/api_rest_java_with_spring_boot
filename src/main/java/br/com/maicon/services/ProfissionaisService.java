package br.com.maicon.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maicon.model.Profissionais;
import br.com.maicon.repositories.ProfissionaisRepository;
import br.com.maicon.util.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * 
 */
@Service
public class ProfissionaisService {

	@Autowired
	private ProfissionaisRepository profissionaisRepository;

	private Logger logger = Logger.getLogger(ProfissionaisService.class.getName());
	
	private final Validator validator;

    public ProfissionaisService(Validator validator) {
        this.validator = validator;
    }

	/**
	 * @return
	 */
	public List<Profissionais> findAll() {
		logger.info("Finding all professionals");

		List<Profissionais> profissionais = new ArrayList<>();
		profissionais = profissionaisRepository.findAll();

		if (profissionais.isEmpty()) {
			throw new RuntimeException("Nenhum profissional encontrado");
		}

		return profissionais;
	}

	/**
	 * @param id
	 * @return
	 */
	public Profissionais findById(Long id) {
		logger.info("Finding professional with ID " + id);
		return profissionaisRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
	}

	/**
	 * @param profissional
	 * @return
	 */
	public Profissionais create(Profissionais profissional) {
		logger.info("Creating one professional");

		Profissionais savedProfissional = profissionaisRepository.save(profissional);

		return savedProfissional;
	}

	
	/**
	 * @param profissional
	 * @return
	 */
	public Profissionais update(Profissionais profissional) {
		Set<ConstraintViolation<Profissionais>> violations = validator.validate(profissional);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Nome do profissional não pode ser vazio ou nulo.");
        }
        
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
