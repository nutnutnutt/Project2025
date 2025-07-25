package com.itsci.project65.service;

import com.itsci.project65.model.Farmer;
import com.itsci.project65.dto.LoginRequest;
import com.itsci.project65.dto.LoginResponse;

public interface FarmerService {
    public Farmer createFarmer(Farmer farmer);
    public Farmer updateFarmer(Farmer farmer);
    public Farmer getFarmerById(int farmerId);
    public void deleteFarmer(int farmerId);
    public Farmer login(String username, String password);
    public LoginResponse authenticateAndGenerateToken(LoginRequest loginRequest);
}
