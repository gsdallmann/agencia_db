package com.agencia.api.dto;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String nome;

    private String email;

    private String senha;

    public String getSenha() {
        return senha;
    }
}
