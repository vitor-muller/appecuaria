package pecuaria.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Alimentacao;
import pecuaria.api.model.Usuario;
import pecuaria.api.repository.AlimentacaoRepository;
import pecuaria.api.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class AlimentacaoService {

    private final AlimentacaoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Alimentacao salvar(Alimentacao alimentacao, String cognitoSubId) {
        Usuario dono = usuarioRepository.findByCognitoSubId(cognitoSubId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        alimentacao.setDono(dono);

        if (alimentacao.getQuantidadeKg() == null || alimentacao.getQuantidadeKg() <= 0) {
            throw new IllegalArgumentException("A quantidade de ração deve ser maior que zero.");
        }
        if (alimentacao.getDataHoraAplicacao() != null && alimentacao.getDataHoraAplicacao().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data/hora da alimentação não pode ser no futuro.");
        }

        return repository.save(alimentacao);
    }

    public List<Alimentacao> listarPorDono(String cognitoSubId) {
        return repository.findByDonoCognitoSubId(cognitoSubId);
    }

    public Optional<Alimentacao> buscarPorId(Long id, String cognitoSubId) {
        return repository.findByIdAndDonoCognitoSubId(id, cognitoSubId);
    }
}