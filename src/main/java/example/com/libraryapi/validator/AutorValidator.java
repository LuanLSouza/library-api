package example.com.libraryapi.validator;

import example.com.libraryapi.exceptions.RegistroDuplicadoException;
import example.com.libraryapi.model.Autor;
import example.com.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AutorValidator {

    private final AutorRepository repository;

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Registro duplicado!!");
        }
    };

    private boolean existeAutorCadastrado(Autor autor){
        var autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());

        if(autor.getId() == null){
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
