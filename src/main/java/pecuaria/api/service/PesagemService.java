package pecuaria.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Pesagem;
import pecuaria.api.model.Usuario;
import pecuaria.api.repository.PesagemRepository;
import pecuaria.api.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class PesagemService {

    private final PesagemRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Pesagem salvar(Pesagem pesagem, String cognitoSubId) {
        Usuario dono = usuarioRepository.findByCognitoSubId(cognitoSubId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        pesagem.setDono(dono);

        if (pesagem.getPesoKg() == null || pesagem.getPesoKg() <= 0) {
            throw new IllegalArgumentException("O peso deve ser maior que zero.");
        }
        if (pesagem.getDataPesagem() != null && pesagem.getDataPesagem().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data da pesagem não pode ser no futuro.");
        }
        return repository.save(pesagem);
    }

    public List<Pesagem> listarPorDono(String cognitoSubId) {
        return repository.findByDonoCognitoSubId(cognitoSubId);
    }

    public Optional<Pesagem> buscarPorId(Long id, String cognitoSubId) {
        return repository.findByIdAndDonoCognitoSubId(id, cognitoSubId);
    }
}
