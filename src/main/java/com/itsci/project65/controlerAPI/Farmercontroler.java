package com.itsci.project65.controlerAPI;


import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.itsci.project65.model.Farmer;
import com.itsci.project65.service.FarmerService;
import com.itsci.project65.dto.LoginRequest;
import com.itsci.project65.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/farmer")
public class Farmercontroler {

    @Autowired
    private FarmerService farmerService;

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
    public ResponseEntity<String> register(@Valid @RequestBody Farmer farmer) {
        farmerService.createFarmer(farmer);
        return new ResponseEntity<>("สมัครสมาชิกเรียบร้อยเเล้ว", HttpStatus.CREATED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("กรุณา กรอกข้อมูลให้ครบ", HttpStatus.BAD_REQUEST);
    }
}


