package br.arturslampert.minisena.modules.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "This field can't be blank")
    private String name;

    @Email(message = "There must be a valid email")
    private String email;

    @Length(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
}
