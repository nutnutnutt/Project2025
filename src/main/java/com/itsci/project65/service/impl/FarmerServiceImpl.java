package com.itsci.project65.service.impl;

import com.itsci.project65.model.Farmer;
import com.itsci.project65.repository.FarmerRepository;
import com.itsci.project65.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmerServiceImpl implements FarmerService {
    @Autowired
    FarmerRepository farmerRepository;

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
}
