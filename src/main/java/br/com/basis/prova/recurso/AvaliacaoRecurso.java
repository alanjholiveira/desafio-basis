package br.com.basis.prova.recurso;

import br.com.basis.prova.dominio.dto.AvaliacaoDTO;
import br.com.basis.prova.servico.AvaliacaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoRecurso {

    private static final String API_AVALIACOES = "/avaliacoes";

    @Autowired
    private AvaliacaoServico avaliacaoServico;

    @PostMapping
    public ResponseEntity<AvaliacaoDTO> salvar(@RequestBody AvaliacaoDTO avaliacaoDTO)  throws URISyntaxException {
        AvaliacaoDTO result = avaliacaoServico.salvar(avaliacaoDTO);
        return ResponseEntity.created(new URI(API_AVALIACOES + result.getId())).body(result);
    }

    @PutMapping
    public ResponseEntity<AvaliacaoDTO> editar(@RequestBody AvaliacaoDTO avaliacaoDTO) throws URISyntaxException {
        AvaliacaoDTO result = this.avaliacaoServico.update(avaliacaoDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluir (@PathVariable("id") Integer id) {
        avaliacaoServico.excluir(id);
        return  ResponseEntity.status(200).build();
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> consultar() {
        return ResponseEntity.ok(this.avaliacaoServico.consultar());
    }

}
