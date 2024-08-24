package br.com.maicon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maicon.model.Profissionais;
import br.com.maicon.repositories.ProfissionaisRepository;

@Service
public class ProfissionaisService {

    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    public Profissionais findById(Long id) {
        return profissionaisRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
    }
}
