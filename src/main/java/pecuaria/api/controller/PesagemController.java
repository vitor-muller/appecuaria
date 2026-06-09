package pecuaria.api.controller;

import java.util.List;

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

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Pesagem;
import pecuaria.api.service.PesagemService;

@RestController
@RequestMapping("/pesagens")
@RequiredArgsConstructor
public class PesagemController {

    private final PesagemService service;

    @GetMapping
    public ResponseEntity<List<Pesagem>> listarTodos(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.listarPorDono(jwt.getSubject()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pesagem> buscarPorId(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return service.buscarPorId(id, jwt.getSubject())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pesagem> cadastrar(@RequestBody Pesagem pesagem, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(pesagem, jwt.getSubject()));
    }
}