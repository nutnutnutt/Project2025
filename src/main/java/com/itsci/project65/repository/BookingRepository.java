package com.itsci.project65.repository;

import com.itsci.project65.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findByFarmer_FarmerIdAndBookingstatus(int farmerId, String bookingStatus);
    List<Booking> findByFarmer_FarmerId(int farmerId);
    @Query("SELECT DISTINCT b FROM Booking b " +
            "JOIN b.bookingEquipments be " +
            "JOIN be.equipment e " +
            "WHERE e.equipmentId = :equipmentId " +
            "AND b.bookingstatus IN ('confirmed', 'pending') " +
            "AND ((b.bookingstartDate BETWEEN :startDate AND :endDate) " +
            "OR (b.bookingendDate BETWEEN :startDate AND :endDate) " +
            "OR (b.bookingstartDate <= :startDate AND b.bookingendDate >= :endDate))")
    List<Booking> findBookingsByEquipmentAndDateRange(
            @Param("equipmentId") Integer equipmentId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * ดึงการจองของอุปกรณ์ในเดือนที่กำหนด
     */
    @Query("SELECT DISTINCT b FROM Booking b " +
            "JOIN b.bookingEquipments be " +
            "JOIN be.equipment e " +
            "WHERE e.equipmentId = :equipmentId " +
            "AND b.bookingstatus IN ('confirmed', 'pending') " +
            "AND ((YEAR(b.bookingstartDate) = :year AND MONTH(b.bookingstartDate) = :month) " +
            "OR (YEAR(b.bookingendDate) = :year AND MONTH(b.bookingendDate) = :month) " +
            "OR (b.bookingstartDate <= :monthStart AND b.bookingendDate >= :monthEnd))")
    List<Booking> findBookingsByEquipmentAndMonth(
            @Param("equipmentId") Integer equipmentId,
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("monthStart") LocalDate monthStart,
            @Param("monthEnd") LocalDate monthEnd);
}