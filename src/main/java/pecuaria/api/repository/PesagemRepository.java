package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pecuaria.api.model.Pesagem;

public interface PesagemRepository extends JpaRepository<Pesagem, Long>{
    List<Pesagem> findByDonoCognitoSubId(String cognitoSubId);
    Optional<Pesagem> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
}
