package pecuaria.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Usuario;
import pecuaria.api.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario obterPorSub(String sub) {
        return repository.findByCognitoSubId(sub)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
    }

    public Usuario salvarOuAtualizar(Usuario dadosRecebidos, String sub) {
        Optional<Usuario> existente = repository.findByCognitoSubId(sub);
        
        if (existente.isPresent()) {
            Usuario usuario = existente.get();
            usuario.setNomeFazenda(dadosRecebidos.getNomeFazenda());
            return repository.save(usuario);
        } 
        
        dadosRecebidos.setCognitoSubId(sub);
        return repository.save(dadosRecebidos);
    }
}