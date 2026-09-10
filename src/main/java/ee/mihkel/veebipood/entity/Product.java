package ee.mihkel.veebipood.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Lombok: getter getId()    setter: setId()
    private String name;
    private double price;
    private String image;
    private Integer stock;
    private Boolean active;

    @ManyToOne
    private Category category;
}
