package com.example.multitenancy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.multitenancy.dto.VoucherDTO;
import com.example.multitenancy.model.Employee;
import com.example.multitenancy.model.Voucher;
import com.example.multitenancy.repository.EmployeeRepository;
import com.example.multitenancy.repository.VoucherRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    public VoucherDTO saveOrUpdateVoucher(VoucherDTO voucherDTO) {
    	Voucher voucher;

    	if (voucherDTO.getVoucherId() != null) {
    		// Update case - Fetch existing record, update fields, and save
    		Optional<Voucher> existingVoucherOpt = voucherRepository.findById(voucherDTO.getVoucherId());
    		if (existingVoucherOpt.isPresent()) {
    			voucher = existingVoucherOpt.get();
    		} else {
    			throw new EntityNotFoundException("Voucher not found for update");
    		}
    	} else {
    		// Insert case - Create a new entity
    		voucher = new Voucher();
    	}

    	// Map fields from DTO to entity
    	voucher.setVoucherDate(voucherDTO.getVoucherDate());
    	voucher.setTotalAmount(voucherDTO.getAmount());
    	voucher.setDescription(voucherDTO.getDescription());
    	voucher.setInvoiceNo(voucherDTO.getInvoiceNo());
    	voucher.setInvoiceDate(voucherDTO.getInvoiceDate());
    	voucher.setInvoiceType(voucherDTO.getVoucherType());
    	// Fetch Employee entity from DB using employeeId from DTO
        Employee employee = employeeRepository.findById(voucherDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        // Set the Employee entity
        voucher.setEmployee(employee);

    	// Save entity
    	Voucher savedVoucher = voucherRepository.save(voucher);

    	// Convert saved entity back to DTO
    	return convertToDTO(savedVoucher);
    	
    }

    public List<VoucherDTO> getAllVouchers() {
    	return voucherRepository.findByDeleteStatus("N").stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteVoucher(Integer voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
            .orElseThrow(() -> new EntityNotFoundException("Voucher not found with ID: " + voucherId));
        
        voucher.setDeleteStatus("Y"); // Mark as deleted
        voucherRepository.save(voucher);
    }
    
    private VoucherDTO convertToDTO(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();
        dto.setVoucherId(voucher.getVoucherId());
        dto.setVoucherDate(voucher.getVoucherDate());
        dto.setAmount(voucher.getTotalAmount());
        dto.setDescription(voucher.getDescription());
        dto.setInvoiceNo(voucher.getInvoiceNo());
        dto.setInvoiceDate(voucher.getInvoiceDate());
        dto.setVoucherType(voucher.getInvoiceType());
        dto.setReason(voucher.getReason());
        dto.setEmployeeId(voucher.getEmployee().getEmployeeId());
        dto.setEmployeeDisplayName(voucher.getEmployee().getEmployeeName() + " (" + voucher.getEmployee().getEmployeeCode() + ")");
        
        return dto;
    }
    
    public VoucherDTO getVoucherById(Integer id) {
        Voucher voucher = voucherRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Voucher not found with ID: " + id));
        
        return convertToDTO(voucher);
    }
}
