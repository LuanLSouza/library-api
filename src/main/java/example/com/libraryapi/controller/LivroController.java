package example.com.libraryapi.controller;

import example.com.libraryapi.controller.dto.CadastroLivroDTO;
import example.com.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import example.com.libraryapi.controller.mappers.LivroMapper;
import example.com.libraryapi.model.GeneroLivro;
import example.com.libraryapi.model.Livro;
import example.com.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nomeAutor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) GeneroLivro genero,
            @RequestParam(value = "anoPublicacao", required = false) Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho
    ){
        var resultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanho);

        Page<ResultadoPesquisaLivroDTO> paginaResponse = resultado.map(mapper::toDTO);

        return ResponseEntity.ok(paginaResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
                                          @RequestBody @Valid CadastroLivroDTO dto){
        return service.findById(UUID.fromString(id))
                .map(livro -> {
                    Livro entity = mapper.toEntity(dto);

                    livro.setDataPublicacao(entity.getDataPublicacao());
                    livro.setIsbn(entity.getIsbn());
                    livro.setPreco(entity.getPreco());
                    livro.setTitulo(entity.getTitulo());
                    livro.setAutor(entity.getAutor());
                    service.atualizar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
