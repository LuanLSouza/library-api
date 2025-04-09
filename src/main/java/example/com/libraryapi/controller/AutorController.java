package example.com.libraryapi.controller;

import example.com.libraryapi.controller.dto.AutorDTO;
import example.com.libraryapi.controller.mappers.AutorMapper;
import example.com.libraryapi.model.Autor;
import example.com.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/autores")
public class AutorController implements GenericController {

    private final AutorService autorService;
    private AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor entity = autorService.salvar(mapper.toEntity(dto));
        URI uri = gerarHeaderLocation(entity.getId());
        return ResponseEntity.created(uri).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> findById(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService.findById(idAutor).map(autor -> {
            AutorDTO dto = mapper.toDTO(autor);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.findById(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.delete(autorOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);

        var resposta = resultado.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(resposta);

    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.findById(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}
