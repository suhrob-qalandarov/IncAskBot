package org.exp.incaskbot.repository;

import org.exp.incaskbot.model.entity.CoinTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinTransactionRepository extends JpaRepository<CoinTransaction, Long> {
}
