package com.example.multitenancy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expense_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_type_id")
    private Integer expenseTypeId;

    @Column(name = "expense_type", nullable = false)
    private String expenseType;

}
