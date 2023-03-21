package com.example.neighbour.repositories;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.Transactions;
import com.example.neighbour.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

    Optional<List<Transactions>> findByToBusiness(Business business);

    Optional<List<Transactions>> findByToBusinessId(Integer businessId);

    Optional<List<Transactions>> findByFromUser(User user);

    Optional<List<Transactions>> findByFromUserId(Integer userId);

}
