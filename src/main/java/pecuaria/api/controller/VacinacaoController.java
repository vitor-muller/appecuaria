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
import pecuaria.api.model.Vacinacao;
import pecuaria.api.service.VacinacaoService;

@RestController
@RequestMapping("/vacinacoes")
@RequiredArgsConstructor
public class VacinacaoController {

    private final VacinacaoService service;

    @GetMapping
    public ResponseEntity<List<Vacinacao>> listarTodos(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.listarPorDono(jwt.getSubject()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacinacao> buscarPorId(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return service.buscarPorId(id, jwt.getSubject())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vacinacao> cadastrar(@RequestBody Vacinacao vacinacao, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(vacinacao, jwt.getSubject()));
    }
}