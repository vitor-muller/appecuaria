package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pecuaria.api.model.Pesagem;

@Repository
public interface PesagemRepository extends JpaRepository<Pesagem, Long>{
    List<Pesagem> findByDonoCognitoSubId(String cognitoSubId);
    Optional<Pesagem> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
}
