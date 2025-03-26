package example.com.libraryapi.service;

import example.com.libraryapi.exceptions.OperacaoInvalidaException;
import example.com.libraryapi.model.Autor;
import example.com.libraryapi.repository.AutorRepository;
import example.com.libraryapi.repository.LivroRepository;
import example.com.libraryapi.validator.AutorValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AutorService {
    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> findById(UUID id) {
        return autorRepository.findById(id);
    }

    public void delete(Autor autor) {
        if(possuiLivro(autor)){
            throw new OperacaoInvalidaException("Autor possui livros cadastrados!");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if(nome != null){
            return autorRepository.findByNome(nome);
        }
        if(nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Autor não existe!");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
