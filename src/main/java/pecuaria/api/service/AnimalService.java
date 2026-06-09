package pecuaria.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Animal;
import pecuaria.api.model.Usuario;
import pecuaria.api.repository.AnimalRepository;
import pecuaria.api.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Animal salvar(Animal animal, String cognitoSubId) {
        Usuario dono = usuarioRepository.findByCognitoSubId(cognitoSubId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (animal.getIdentificacao() == null || animal.getIdentificacao().trim().isEmpty()) {
            throw new IllegalArgumentException("A identificação (brinco) é obrigatória.");
        }

        animal.setDono(dono);
        return repository.save(animal);
    }

    public List<Animal> listarPorDono(String cognitoSubId) {
        return repository.findByDonoCognitoSubId(cognitoSubId);
    }

    public Optional<Animal> buscarPorId(Long id, String cognitoSubId) {
        return repository.findByIdAndDonoCognitoSubId(id, cognitoSubId);
    }
}
