package pecuaria.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Lote;
import pecuaria.api.model.Usuario;
import pecuaria.api.repository.LoteRepository;
import pecuaria.api.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Lote salvar(Lote lote, String cognitoSubId) {
        Usuario dono = usuarioRepository.findByCognitoSubId(cognitoSubId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        lote.setDono(dono);
        return repository.save(lote);
    }

    public List<Lote> listarPorDono(String cognitoSubId) {
        return repository.findByDonoCognitoSubId(cognitoSubId);
    }

    public Optional<Lote> buscarPorId(Long id, String cognitoSubId) {
        return repository.findByIdAndDonoCognitoSubId(id, cognitoSubId);
    }
}