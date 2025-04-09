package example.com.libraryapi.controller.mappers;

import example.com.libraryapi.controller.dto.AutorDTO;
import example.com.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
