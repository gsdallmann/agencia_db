package com.agencia.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "destinos")
public class Destino {

    // Getters e Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do destino é obrigatório.")
    @Size(max = 100, message = "O nome do destino deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "A localização do destino é obrigatória.")
    @Size(max = 150, message = "A localização deve ter no máximo 150 caracteres.")
    private String localizacao;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    private double nota = 0.0; // Nota média

    public Destino(Object o, String nome, String localizacao, String descricao) {
    }

}
