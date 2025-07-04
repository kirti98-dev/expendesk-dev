package com.example.multitenancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.multitenancy.model.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
	List<Voucher> findByDeleteStatus(String deleteStatus);
}
