package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MarketRepository extends JpaRepository<Market, UUID> {
    List<Market> findByAddressContaining(String region);
    Optional<Market> findByName(String marketName);
    List<Market> findAllByName(String marketName);
}
