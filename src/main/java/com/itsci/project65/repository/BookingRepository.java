package com.itsci.project65.repository;

import com.itsci.project65.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findByFarmer_FarmerIdAndBookingstatus(int farmerId, String bookingStatus);
    List<Booking> findByFarmer_FarmerId(int farmerId);

}
