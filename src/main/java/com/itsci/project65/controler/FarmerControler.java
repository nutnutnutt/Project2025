package com.itsci.project65.controler;


import com.itsci.project65.model.Farmer;
import com.itsci.project65.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/farmer")
public class FarmerControler {

    @Autowired
    FarmerService farmerService;

    @PostMapping("/create")
    ResponseEntity<?> createFarmer(@RequestBody Farmer farmer){
        try{

            Farmer createFarmer = farmerService.createFarmer(farmer);
            if (createFarmer == null){
                return new ResponseEntity<>("Cannot add Farmer", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(createFarmer,HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getFarmerById(@PathVariable("id") int id ){
        try{
            Farmer farmer = farmerService.getFarmerById(id);
            if (farmer == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return  new ResponseEntity<>(farmer,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    ResponseEntity<?> updateFarmer(@RequestBody Farmer farmer){
        try {
            Farmer exitstingFarmer = farmerService.getFarmerById(farmer.getFarmerId());

            if (exitstingFarmer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            exitstingFarmer.setFarmerAlley(farmer.getFarmerAlley());
            exitstingFarmer.setFarmerDistrict(farmer.getFarmerDistrict());
            exitstingFarmer.setFarmerEmail(farmer.getFarmerEmail());
            exitstingFarmer.setFarmerDOB(farmer.getFarmerDOB());
            exitstingFarmer.setFarmerGender(farmer.getFarmerGender());
            exitstingFarmer.setFarmerFName(farmer.getFarmerFName());
            exitstingFarmer.setFarmerLName(farmer.getFarmerLName());
            exitstingFarmer.setFarmerMoo(farmer.getFarmerMoo());
            exitstingFarmer.setFarmerHouseNumber(farmer.getFarmerHouseNumber());
            exitstingFarmer.setFarmerProvince(farmer.getFarmerProvince());
            exitstingFarmer.setFarmerPostalCode(farmer.getFarmerPostalCode());
            exitstingFarmer.setFarmerSubDistrict(farmer.getFarmerSubDistrict());
            exitstingFarmer.setFarmerTel(farmer.getFarmerTel());

            Farmer updateFarmer = farmerService.updateFarmer(exitstingFarmer);

            return new ResponseEntity<>(updateFarmer,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteFarmer(@PathVariable("id") int id){
        try{
            farmerService.deleteFarmer(id);
            return new ResponseEntity<>("DELETED FARMER SUCCESSFUL",HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
