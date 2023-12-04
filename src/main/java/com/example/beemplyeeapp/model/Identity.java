package com.example.beemplyeeapp.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
@Entity(name="identities")
@Table(name = "identities")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Identity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] image;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    // Constructor
    public Identity(String email, byte[] image,Account account) {
        this.email = email;
        this.image = Arrays.copyOf(image, image.length);
        this.account=account;
    }

}
