package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pecuaria.api.model.Piquete;

@Repository
public interface PiqueteRepository extends JpaRepository<Piquete, Long>{
    List<Piquete> findByDonoCognitoSubId(String cognitoSubId);
    Optional<Piquete> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
}
