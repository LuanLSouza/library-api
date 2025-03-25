package example.com.libraryapi.controller.dto;

import example.com.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setNacionalidade(this.nacionalidade);
        autor.setDataNascimento(this.dataNascimento);

        return autor;
    }

    public static AutorDTO toResponse(Autor entity){
        return new AutorDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDataNascimento(),
                entity.getNacionalidade());
    }
}
