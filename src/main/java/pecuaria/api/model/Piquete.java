package pecuaria.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Piquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double areaHectares;
    private Integer capacidadeCabecas;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dono_id", nullable = false)
    private Usuario dono;
}
