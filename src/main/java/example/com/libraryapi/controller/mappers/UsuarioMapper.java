package example.com.libraryapi.controller.mappers;

import example.com.libraryapi.controller.dto.UsuarioDTO;
import example.com.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

}
