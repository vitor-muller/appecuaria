package pecuaria.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pecuaria.api.model.TratamentoVeterinario;

public interface TratamentoVeterinarioRepository extends JpaRepository<TratamentoVeterinario, Long>{
    List<TratamentoVeterinario> findByDonoCognitoSubId(String cognitoSubId);
    Optional<TratamentoVeterinario> findByIdAndDonoCognitoSubId(Long id, String cognitoSubId);
}
