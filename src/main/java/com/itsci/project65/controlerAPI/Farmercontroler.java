package com.itsci.project65.controlerAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsci.project65.model.Farmer;
import com.itsci.project65.service.FarmerService;
import com.itsci.project65.dto.LoginRequest;
import com.itsci.project65.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/farmer")
public class Farmercontroler {

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = farmerService.authenticateAndGenerateToken(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam("farmer") String farmerStr, @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Farmer farmer = objectMapper.readValue(farmerStr, Farmer.class);
            farmerService.createFarmer(farmer, file);
            return new ResponseEntity<>("สมัครสมาชิกเรียบร้อยแล้ว", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Error parsing farmer data: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{farmerId}")
    public ResponseEntity<Farmer> updateFarmer(@PathVariable int farmerId, @RequestParam("farmer") String farmerStr, @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Farmer farmerDetails = objectMapper.readValue(farmerStr, Farmer.class);
            Farmer updatedFarmer = farmerService.updateFarmer(farmerId, farmerDetails, file);
            return new ResponseEntity<>(updatedFarmer, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{farmerId}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable int farmerId) {
        Farmer farmer = farmerService.getFarmerById(farmerId);
        return new ResponseEntity<>(farmer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{farmerId}")
    public ResponseEntity<String> deleteFarmer(@PathVariable int farmerId) {
        farmerService.deleteFarmer(farmerId);
        return new ResponseEntity<>("Farmer deleted successfully", HttpStatus.OK);
    }
}


