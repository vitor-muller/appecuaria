package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pecuaria.api.model.Lote;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
    List<Lote> findByDonoCognitoSubId(String cognitoSubId);
    Optional<Lote> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
}
