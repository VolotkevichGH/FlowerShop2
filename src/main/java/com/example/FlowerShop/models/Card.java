package com.example.FlowerShop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String dateOfLicense;
//    private String month;
    private String cvv;
    private String name;
    private String code;
    private String resultcheck;
}
