package com.example.beemplyeeapp.model;
import com.example.beemplyeeapp.utils.PasswordEncryption;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name="accounts")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="email")
    private String email;

    @JsonProperty("password")
    private String password;

    @Column(name = "CNP")
    private String cnp;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Identity identity;
    public Account(String email, String password, String cnp) {
        this.email = email;
        this.password = password;
        this.cnp = cnp;
    }
    public String getPasswordDecrypted() {
        return PasswordEncryption.decryptPassword(password);
    }
    public void setPassword(String password) {
        this.password = PasswordEncryption.encryptPassword(password);
    }


}
