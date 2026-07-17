package one.digitalinovation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import one.digitalinovation.enums.TipoAnimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String raca;
    private TipoAnimal tipoAnimal;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente dono;
}
