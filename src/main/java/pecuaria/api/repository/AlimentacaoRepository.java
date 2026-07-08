package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pecuaria.api.model.Alimentacao;

public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {
    List<Alimentacao> findByDonoCognitoSubId(String cognitoSubId);
    Optional<Alimentacao> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
}
