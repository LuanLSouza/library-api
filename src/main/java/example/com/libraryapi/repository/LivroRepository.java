package example.com.libraryapi.repository;

import example.com.libraryapi.model.Autor;
import example.com.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    boolean existsByAutor(Autor autor);
}
