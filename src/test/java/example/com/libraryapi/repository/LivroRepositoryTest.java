package example.com.libraryapi.repository;

import example.com.libraryapi.model.Autor;
import example.com.libraryapi.model.GeneroLivro;
import example.com.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = Livro.builder()
                .isbn("90888-1565")
                .preco(BigDecimal.valueOf(100))
                .titulo("Game Of Thrones")
                .dataPublicacao(LocalDate.of(2014, 5, 1))
                .genero(GeneroLivro.FANTASIA)
                .build();

        //var autor = autorRepository.findById(UUID.fromString("38d88ab7-f818-41a9-83c7-430d3ebc68a1")).orElse(null);

        livro.setAutor(new Autor());

        repository.save(livro);

        System.out.println("Livro salvo: ");
        System.out.println(livro);
    }
    @Test
    void salvarCascadeTest(){
        Livro livro = Livro.builder()
                .isbn("90888-1565")
                .preco(BigDecimal.valueOf(100))
                .titulo("Livro 10")
                .dataPublicacao(LocalDate.of(2014, 5, 1))
                .genero(GeneroLivro.FANTASIA)
                .build();

//        Autor autor = new Autor();
//        autor.setNome("João");
//        autor.setNacionalidade("Brasileiro");
//        autor.setDataNascimento(LocalDate.of(2003, 2, 15));


        //livro.setAutor(autor);

        repository.save(livro);

        System.out.println(livro);
    }
}