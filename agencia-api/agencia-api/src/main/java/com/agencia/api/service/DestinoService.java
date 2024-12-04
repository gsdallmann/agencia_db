package com.agencia.api.service;

import com.agencia.api.model.Destino;
import com.agencia.api.repository.DestinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinoService {

    private final DestinoRepository destinoRepository;

    public DestinoService(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    public Destino cadastrarDestino(Destino destino) {
        return destinoRepository.save(destino);
    }

    public List<Destino> listarDestinos() {
        return destinoRepository.findAll();
    }

    public List<Destino> pesquisarPorTermo(String termo) {
        return destinoRepository.findByNomeContainingOrLocalizacaoContaining(termo, termo);
    }

    public Optional<Destino> buscarPorId(Long id) {
        return destinoRepository.findById(id);
    }

    public boolean avaliarDestino(Long id, double nota) {
        Optional<Destino> destinoOptional = destinoRepository.findById(id);
        if (destinoOptional.isPresent()) {
            Destino destino = destinoOptional.get();
            destino.setNota((destino.getNota() + nota) / 2); // Média das notas
            destinoRepository.save(destino);
            return true;
        }
        return false;
    }

    public boolean excluirDestino(Long id) {
        if (destinoRepository.existsById(id)) {
            destinoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
