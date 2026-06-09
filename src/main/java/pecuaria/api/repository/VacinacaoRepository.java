package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pecuaria.api.model.Vacinacao;

@Repository
public interface VacinacaoRepository extends JpaRepository<Vacinacao, Long> {
    List<Vacinacao> findByDonoCognitoSubId(String cognitoSubId);
    Optional<Vacinacao> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
}
