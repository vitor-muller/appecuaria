package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pecuaria.api.model.TratamentoVeterinario;

@Repository
public interface TratamentoVeterinarioRepository extends JpaRepository<TratamentoVeterinario, Long>{
    List<TratamentoVeterinario> findByDonoCognitoSubId(String cognitoSubId);
    Optional<TratamentoVeterinario> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
}
