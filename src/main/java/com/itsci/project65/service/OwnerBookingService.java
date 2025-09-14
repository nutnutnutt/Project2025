package com.itsci.project65.service;

import com.itsci.project65.dto.BookingWithEquipmentDTO;
import com.itsci.project65.dto.OwnerBookingDetailDTO;

import java.util.List;

public interface OwnerBookingService {
    
    /**
     * Get all bookings for equipment owned by the specified owner
     * @param ownerId Owner ID
     * @return List of bookings with equipment details
     */
    List<BookingWithEquipmentDTO> getBookingsByOwnerId(int ownerId);
    
    /**
     * Get detailed booking information by booking ID
     * @param bookingId Booking ID
     * @return Detailed booking information with equipment and farmer details
     */
    OwnerBookingDetailDTO getBookingDetailById(int bookingId);
    
    /**
     * Update booking status
     * @param bookingId Booking ID
     * @param status New status
     * @return true if updated successfully, false otherwise
     */
    boolean updateBookingStatus(int bookingId, String status);
    
    /**
     * Check if booking belongs to owner's equipment
     * @param bookingId Booking ID
     * @param ownerId Owner ID
     * @return true if booking belongs to owner, false otherwise
     */
    boolean isBookingBelongsToOwner(int bookingId, int ownerId);
}
