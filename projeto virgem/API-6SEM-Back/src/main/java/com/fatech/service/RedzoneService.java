package com.fatech.service;

import com.fatech.entity.Redzone;
import com.fatech.repository.RedzoneRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RedzoneService {

    @Autowired
    private RedzoneRepository redzoneRepository;

    public Redzone criarRedzone(Redzone redzone) {

        if (redzone == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Redzone não pode ser nulo");
        }
        if (redzone.getNome_redzone() == null || redzone.getNome_redzone().isBlank() ||
                redzone.getCamera() == null || redzone.getCamera().isBlank() ||
                redzone.getCapacidade_maxima() <= 0 ||
                redzone.getId_departamento() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Todos os campos obrigatórios devem ser fornecidos");
        }

        return redzoneRepository.save(redzone);
    }

    public List<Redzone> buscarTodasRedzones() {
        List<Redzone> redzones = redzoneRepository.findAll();
        if (redzones.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma redzone encontrada");
        }
        return redzones;
    }

    public Redzone buscarRedzonePorId(long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de redzone inválido");
        }
        Optional<Redzone> redzoneOptional = redzoneRepository.findById(id);
        if (redzoneOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Redzone não encontrada para o ID fornecido");
        }
        return redzoneOptional.get();
    }

    public void excluirRedzone(long id) {

        if (!redzoneRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Redzone não encontrada");
        }
        redzoneRepository.deleteById(id);
    }

    public void deletarRedzone(long id) {
        Optional<Redzone> optionalRedzone = redzoneRepository.findById(id);
        if (optionalRedzone.isPresent()) {
            Redzone redzone = optionalRedzone.get();
            redzone.desativar();
            redzoneRepository.save(redzone);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Redzone não encontrada");
        }
    }

    public Redzone atualizarRedzone(Redzone redzone) {

        if (redzone.getId_redzone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID da redzone não fornecido");
        }
        if (redzone.getNome_redzone() == null ||
                redzone.getNome_redzone().isBlank() ||
                redzone.getCamera() == null || 
                redzone.getCamera().isBlank() ||
                redzone.getCapacidade_maxima() <= 0 ||
                redzone.getId_departamento() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Todos os campos obrigatórios devem ser fornecidos");
        }

        if (!redzoneRepository.existsById(redzone.getId_redzone())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Redzone não encontrada");
        }

        Redzone redzoneExistente = redzoneRepository.findById(redzone.getId_redzone()).get();

        redzoneExistente.setNome_redzone(redzone.getNome_redzone());
        redzoneExistente.setCamera(redzone.getCamera());
        redzoneExistente.setCapacidade_maxima(redzone.getCapacidade_maxima());
        redzoneExistente.setId_departamento(redzone.getId_departamento());

  
        if (redzone.getResponsavel_id() != null) {
            redzoneExistente.setResponsavel_id(redzone.getResponsavel_id());
        }
        redzoneExistente.setDelete_at(redzone.getDelete_at());

        return redzoneRepository.save(redzoneExistente);
    }

}
