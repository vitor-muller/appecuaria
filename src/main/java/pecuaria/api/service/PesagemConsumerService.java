package pecuaria.api.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import pecuaria.api.model.Animal;
import pecuaria.api.model.Pesagem;
import pecuaria.api.repository.AnimalRepository;
import pecuaria.api.repository.PesagemRepository;

@Service
@RequiredArgsConstructor
public class PesagemConsumerService {

    private final ObjectMapper objectMapper;
    private final PesagemRepository pesagemRepository;
    private final AnimalRepository animalRepository;

    public record PesagemIoTDto(String rfidAnimal, Double pesoKg, String donoId, String dataHoraLeitura) {}

    @RabbitListener(queues = "fila_pesagem_core")
    public void receberPesagemDoSensor(String mensagemJson) {
        try {
            System.out.println("Processando nova pesagem do RabbitMQ...");

            PesagemIoTDto dados = objectMapper.readValue(mensagemJson, PesagemIoTDto.class);

            Optional<Animal> animalOpt = animalRepository.findByIdentificacaoAndDonoId(dados.rfidAnimal(), dados.donoId());

            if (animalOpt.isPresent()) {
                Animal animal = animalOpt.get();

                Pesagem novaPesagem = new Pesagem();
                novaPesagem.setPesoKg(dados.pesoKg());
                novaPesagem.setDataPesagem(LocalDate.now()); 
                novaPesagem.setAnimal(animal);
                novaPesagem.setDono(animal.getDono());

                pesagemRepository.save(novaPesagem);
                System.out.println("Pesagem de " + dados.pesoKg() + "kg salva com sucesso para o animal " + dados.rfidAnimal());

            } else {
                System.err.println("Animal com RFID " + dados.rfidAnimal() + " não encontrado para o produtor " + dados.donoId());
            }

        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem do RabbitMQ: " + e.getMessage());
        }
    }
}