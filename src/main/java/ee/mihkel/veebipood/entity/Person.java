package ee.mihkel.veebipood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // kui Person kustutatakse, siis Address kustub
    // kui Person muudetakse, siis on võimalik ka Addressi läbi tema muuta
    // kui Person lisatakse, siis on võimalik ka Addressi läbi tema lisada
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}
