package pecuaria.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Piquete;
import pecuaria.api.model.Usuario;
import pecuaria.api.repository.PiqueteRepository;
import pecuaria.api.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class PiqueteService {

    private final PiqueteRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Piquete salvar(Piquete piquete, String cognitoSubId) {
        if (piquete.getAreaHectares() != null && piquete.getAreaHectares() <= 0) {
            throw new IllegalArgumentException("A área do piquete deve ser maior que zero.");
        }
        if (piquete.getCapacidadeCabecas() != null && piquete.getCapacidadeCabecas() <= 0) {
            throw new IllegalArgumentException("A capacidade de cabeças deve ser maior que zero.");
        }

        Usuario dono = usuarioRepository.findByCognitoSubId(cognitoSubId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        piquete.setDono(dono);
        return repository.save(piquete);
    }

    public List<Piquete> listarPorDono(String cognitoSubId) {
        return repository.findByDonoCognitoSubId(cognitoSubId);
    }

    public Optional<Piquete> buscarPorId(Long id, String cognitoSubId) {
        return repository.findByIdAndDonoCognitoSubId(id, cognitoSubId);
    }
}
