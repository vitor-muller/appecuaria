package pecuaria.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.TratamentoVeterinario;
import pecuaria.api.model.Usuario;
import pecuaria.api.repository.TratamentoVeterinarioRepository;
import pecuaria.api.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class TratamentoVeterinarioService {

    private final TratamentoVeterinarioRepository repository;
    private final UsuarioRepository usuarioRepository;

    public TratamentoVeterinario salvar(TratamentoVeterinario tratamento, String cognitoSubId) {
        if (tratamento.getMedicamento() == null || tratamento.getMedicamento().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do medicamento é obrigatório.");
        }
        if (tratamento.getDataTratamento() != null && tratamento.getDataTratamento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data do tratamento não pode ser no futuro.");
        }

        Usuario dono = usuarioRepository.findByCognitoSubId(cognitoSubId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        tratamento.setDono(dono);
        return repository.save(tratamento);
    }

    public List<TratamentoVeterinario> listarPorDono(String cognitoSubId) {
        return repository.findByDonoCognitoSubId(cognitoSubId);
    }

    public Optional<TratamentoVeterinario> buscarPorId(Long id, String cognitoSubId) {
        return repository.findByIdAndDonoCognitoSubId(id, cognitoSubId);
    }
}
