package example.com.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "Campo obrigatório")
        String login,
        @NotBlank(message = "Campo obrigatório")
        String senha,
        @NotBlank(message = "Campo obrigatório")
        @Email (message = "Invalido")
        String email,
        List<String> roles) {
}
