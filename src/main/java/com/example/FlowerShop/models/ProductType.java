package com.example.FlowerShop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_type")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short id;
    private String name;
}
