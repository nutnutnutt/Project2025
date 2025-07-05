package com.itsci.project65.service;

import com.itsci.project65.model.Farmer;

public interface FarmerService {
    public Farmer createFarmer(Farmer farmer);
    public Farmer updateFarmer(Farmer farmer);
    public Farmer getFarmerById(int farmerId);
    public void deleteFarmer(int farmerId);

}
