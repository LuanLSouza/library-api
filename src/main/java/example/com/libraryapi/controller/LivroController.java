package example.com.libraryapi.controller;

import example.com.libraryapi.controller.dto.CadastroLivroDTO;
import example.com.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import example.com.libraryapi.controller.mappers.LivroMapper;
import example.com.libraryapi.model.GeneroLivro;
import example.com.libraryapi.model.Livro;
import example.com.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@Valid @RequestBody CadastroLivroDTO dto) {

        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> findById(@PathVariable("id") String id){
        return service.findById(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        return service.findById(UUID.fromString(id))
                .map(livro -> {
                    service.delete(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nomeAutor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) GeneroLivro genero,
            @RequestParam(value = "anoPublicacao", required = false) Integer anoPublicacao
    ){
        var resultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);
        var lista = resultado.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(lista);
    }
}
