package com.example.multitenancy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.multitenancy.model.ExpenseType;
import com.example.multitenancy.service.ExpenseTypeService;

@RestController
@RequestMapping("/api/expense-types")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @GetMapping("/all")
    public List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeService.getAllExpenseTypes();
    }
}
