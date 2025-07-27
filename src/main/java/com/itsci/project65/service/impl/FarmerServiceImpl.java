package com.itsci.project65.service.impl;

import com.itsci.project65.model.Farmer;
import com.itsci.project65.repository.FarmerRepository;
import com.itsci.project65.service.FarmerService;
import com.itsci.project65.dto.LoginRequest;
import com.itsci.project65.dto.LoginResponse;
import com.itsci.project65.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmerServiceImpl implements FarmerService {
    @Autowired
    FarmerRepository farmerRepository;
    
    @Autowired
    JwtUtil jwtUtil;

    

    @Override
    public Farmer createFarmer(Farmer farmer) {
        return farmerRepository.save(farmer);
    }

    @Override
    public Farmer updateFarmer(Farmer farmer) {
        return farmerRepository.save(farmer);
    }

    @Override
    public Farmer getFarmerById(int farmerId) {
        return farmerRepository.getReferenceById(farmerId);
    }

    @Override
    public void deleteFarmer(int farmerId) {
        Farmer existingFarmer = farmerRepository.getReferenceById(farmerId);
        farmerRepository.delete(existingFarmer);
    }

    @Override
    public Farmer login(String username, String password) {
        Farmer farmer = farmerRepository.findByFarmerUserName(username);
        System.out.println("farmer:" + farmer);
        System.out.println("password:" + password);
        System.out.println("farmerPassword:" + farmer.getFarmerPassword());
        if (farmer != null && farmer.getFarmerPassword() != null && farmer.getFarmerPassword().equals(password)) {
            return farmer;
        }
        return null;
    }

    @Override
    public LoginResponse authenticateAndGenerateToken(LoginRequest loginRequest) {
        Farmer farmer = login(loginRequest.getFarmerUserName(), loginRequest.getFarmerPassword());
        
        if (farmer != null) {
            String token = jwtUtil.generateTokenForFarmer(farmer.getFarmerUserName(), farmer.getFarmerId());
            return new LoginResponse(token, farmer.getFarmerId(), farmer.getFarmerUserName(), "เข้าสู่ระบบสำเร็จ");
        } else {
            throw new RuntimeException("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
        }
    }
}