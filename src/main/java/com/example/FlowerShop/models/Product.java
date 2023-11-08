package com.example.FlowerShop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private String descrip;
    private String description;
    private String image;
    private Long totalPrice;
    private Long purchasesCount = 0L;
    @ManyToOne
    private ProductType type;
    private LocalDateTime dateOfCreate;
}
