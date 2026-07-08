package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pecuaria.api.model.Animal;


public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByDonoCognitoSubId(String cognitoSubId);
    Optional<Animal> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
    Optional<Animal> findByIdentificacaoAndDonoCognitoSubId(String identificacao, String donoId);
}
