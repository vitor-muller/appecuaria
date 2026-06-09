package pecuaria.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pecuaria.api.model.Lote;
import pecuaria.api.service.LoteService;

@RestController
@RequestMapping("/lotes")
public class LoteController {

    @Autowired
    private LoteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Lote> buscarPorId(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return service.buscarPorId(id, jwt.getSubject())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lote> cadastrar(@RequestBody Lote lote, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(lote, jwt.getSubject()));
    }

    @GetMapping
    public ResponseEntity<List<Lote>> listarTodos(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.listarPorDono(jwt.getSubject()));
    }
}