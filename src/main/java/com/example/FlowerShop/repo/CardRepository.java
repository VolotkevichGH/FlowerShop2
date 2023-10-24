package com.example.FlowerShop.repo;

import com.example.FlowerShop.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByNumber(String cardTest);
}
