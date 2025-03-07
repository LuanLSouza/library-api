package example.com.libraryapi.repository;

import example.com.libraryapi.model.Autor;
import example.com.libraryapi.model.GeneroLivro;
import example.com.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Robinho");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 2, 15));

        Autor autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        UUID id = UUID.fromString("38d88ab7-f818-41a9-83c7-430d3ebc68a1");

        var possivelAutor = repository.findById(id);
        if(possivelAutor.isPresent()){
            var autor = possivelAutor.get();
            System.out.println(autor);

            autor.setDataNascimento(LocalDate.of(1970, 5, 1));

            repository.save(autor);
        }

    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deleteTest(){
        UUID id = UUID.fromString("3f7cd54d-7147-4ea7-90ce-8ec4f40230de");
        repository.deleteById(id);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Marinho");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1987, 2, 15));

        Livro livro = Livro.builder()
                .isbn("18462-1565")
                .preco(BigDecimal.valueOf(47))
                .titulo("Livro 3")
                .dataPublicacao(LocalDate.of(2019, 5, 1))
                .genero(GeneroLivro.CIENCIA)
                .autor(autor)
                .build();

        Livro livro2 = Livro.builder()
                .isbn("18462-1565")
                .preco(BigDecimal.valueOf(47))
                .titulo("Livro 4")
                .dataPublicacao(LocalDate.of(2022, 5, 1))
                .genero(GeneroLivro.ROMANCE)
                .autor(autor)
                .build();

        autor.setLivros(new ArrayList<>());

        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }
}
