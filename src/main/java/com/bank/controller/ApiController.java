package com.bank.controller;

import com.bank.service.ExcelDataService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ApiController {
    private final ExcelDataService excelDataService;

    @GetMapping("getBankingDeviceList")
    public ResponseEntity<?> getBankingDeviceList(){
        return new ResponseEntity<>(excelDataService.findAll(), HttpStatus.OK);
    }
}
