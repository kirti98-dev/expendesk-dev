package com.example.multitenancy.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.multitenancy.model.ExpenseType;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Integer> {
    List<ExpenseType> findAll();
}