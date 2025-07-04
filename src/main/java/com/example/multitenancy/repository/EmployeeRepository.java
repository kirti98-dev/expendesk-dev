package com.example.multitenancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.multitenancy.dto.EmployeeDTO;
import com.example.multitenancy.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Employee findByEmailIdAndPassword(String emailId, String password);

	@Query("SELECT new com.example.multitenancy.dto.EmployeeDTO(e.employeeId, e.employeeName, e.employeeCode) " +
			"FROM Employee e WHERE LOWER(e.employeeName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	List<EmployeeDTO> findEmployeesByName(@Param("searchTerm") String searchTerm);
}
