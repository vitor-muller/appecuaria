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
import pecuaria.api.model.TratamentoVeterinario;
import pecuaria.api.service.TratamentoVeterinarioService;

@RestController
@RequestMapping("/tratamentos")
@RequiredArgsConstructor
public class TratamentoVeterinarioController {

    private final TratamentoVeterinarioService service;

    @GetMapping
    public ResponseEntity<List<TratamentoVeterinario>> listarTodos(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.listarPorDono(jwt.getSubject()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamentoVeterinario> buscarPorId(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return service.buscarPorId(id, jwt.getSubject())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TratamentoVeterinario> cadastrar(@RequestBody TratamentoVeterinario tratamento, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(tratamento, jwt.getSubject()));
    }
}