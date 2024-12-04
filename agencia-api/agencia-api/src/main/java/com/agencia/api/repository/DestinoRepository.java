package com.agencia.api.repository;

import com.agencia.api.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
    List<Destino> findByNomeContainingOrLocalizacaoContaining(String nome, String localizacao);
}
