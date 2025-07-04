package com.example.multitenancy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.multitenancy.dto.VoucherDTO;
import com.example.multitenancy.service.VoucherService;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {
	
    @Autowired
    private VoucherService voucherService;

    @PostMapping("/save")
    public ResponseEntity<VoucherDTO> saveVoucher(@RequestBody VoucherDTO voucherDTO) {
        VoucherDTO savedVoucher = voucherService.saveOrUpdateVoucher(voucherDTO);
        return ResponseEntity.ok(savedVoucher);
    }

    @GetMapping("/all")
    public List<VoucherDTO> getAllVouchers() {
        return voucherService.getAllVouchers();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable Integer id) {
        VoucherDTO voucherDTO = voucherService.getVoucherById(id);
        return ResponseEntity.ok(voucherDTO);
    }
    
    @PostMapping("/delete")
    public ResponseEntity<String> deleteVoucher(@RequestBody Map<String, Integer> requestBody) {
        Integer voucherId = requestBody.get("voucherId");
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.ok("Voucher deleted successfully");
    }
}
