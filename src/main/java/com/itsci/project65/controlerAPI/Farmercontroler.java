package com.itsci.project65.controlerAPI;


import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.itsci.project65.model.Farmer;
import com.itsci.project65.service.FarmerService;
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
    public ResponseEntity<String> login(@RequestBody Farmer farmer) {
        Farmer loggedInFarmer = farmerService.login(farmer.getFarmerUserName(), farmer.getFarmerPassword());

        if (loggedInFarmer != null) {
            return new ResponseEntity<>("เข้าสู่ระบบสำเร็จ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ชื่อผู้ใช้กับรหัสผ่านไม่ถูกต้อง", HttpStatus.UNAUTHORIZED);
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