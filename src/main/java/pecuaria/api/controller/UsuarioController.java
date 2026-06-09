package pecuaria.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pecuaria.api.model.Usuario;
import pecuaria.api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> obterMeuPerfil(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.obterPorSub(jwt.getSubject()));
    }

    @PostMapping
    public ResponseEntity<Usuario> atualizarPerfil(@RequestBody Usuario usuario, @AuthenticationPrincipal Jwt jwt) {
        usuario.setCognitoSubId(jwt.getSubject());
        return ResponseEntity.ok(service.salvarOuAtualizar(usuario, jwt.getSubject()));
    }
}