package example.com.libraryapi.controller.dto;

import example.com.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,
                       //para strings para nao vir nula ou vazia
                       @NotBlank(message = "Campo obrigatório") String nome,
                       @NotNull(message = "Campo obrigatório") LocalDate dataNascimento,
                       @NotBlank(message = "Campo obrigatório") String nacionalidade) {

    public static AutorDTO toResponse(Autor entity){
        return new AutorDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDataNascimento(),
                entity.getNacionalidade());
    }
}
