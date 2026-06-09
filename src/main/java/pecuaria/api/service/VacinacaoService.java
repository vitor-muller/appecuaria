package pecuaria.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Usuario;
import pecuaria.api.model.Vacinacao;
import pecuaria.api.repository.UsuarioRepository;
import pecuaria.api.repository.VacinacaoRepository;

@Service
@RequiredArgsConstructor
public class VacinacaoService {

    private final VacinacaoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Vacinacao salvar(Vacinacao vacinacao, String cognitoSubId) {
        if (vacinacao.getNomeVacina() == null || vacinacao.getNomeVacina().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da vacina é obrigatório.");
        }
        if (vacinacao.getDataAplicacao() != null && vacinacao.getDataAplicacao().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de aplicação não pode ser no futuro.");
        }

        Usuario dono = usuarioRepository.findByCognitoSubId(cognitoSubId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        vacinacao.setDono(dono);
        return repository.save(vacinacao);
    }

    public List<Vacinacao> listarPorDono(String cognitoSubId) {
        return repository.findByDonoCognitoSubId(cognitoSubId);
    }

    public Optional<Vacinacao> buscarPorId(Long id, String cognitoSubId) {
        return repository.findByIdAndDonoCognitoSubId(id, cognitoSubId);
    }
}
