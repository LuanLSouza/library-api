package example.com.libraryapi.service;

import example.com.libraryapi.model.GeneroLivro;
import example.com.libraryapi.model.Livro;
import example.com.libraryapi.repository.LivroRepository;
import example.com.libraryapi.repository.specs.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static example.com.libraryapi.repository.specs.LivroSpecs.*;


@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> findById(UUID id){
        return repository.findById(id);
    }

    public void delete(Livro entity) {
        repository.delete(entity);
    }

    public List<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicacao){
//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero));

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if (anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return repository.findAll(specs);

    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Livro não existe!");
        }
        repository.save(livro);
    }
}
