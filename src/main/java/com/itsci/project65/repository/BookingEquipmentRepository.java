package com.itsci.project65.repository;

import com.itsci.project65.model.BookingEquipment;
import com.itsci.project65.model.BookingEquipmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingEquipmentRepository extends JpaRepository<BookingEquipment, BookingEquipmentId> {

    // หา equipment ที่อยู่ใน booking หนึ่ง ๆ
    List<BookingEquipment> findByBooking_BookingId(int bookingId);

    // หา booking ที่จอง equipment ตัวหนึ่ง ๆ
    List<BookingEquipment> findByEquipment_EquipmentId(int equipmentId);
    
    // หา booking ที่จองอุปกรณ์ของ owner คนหนึ่ง ๆ
    List<BookingEquipment> findByEquipment_EquipmentOwner_OwnerId(int ownerId);

    @Query("""
        select distinct be.equipment.equipmentId
        from BookingEquipment be
        join be.booking b
        where lower(coalesce(b.bookingstatus,'')) <> lower(:status)
    """)
    List<Integer> findEquipmentIdsByNotStatus(@Param("status") String status);

    // จำกัดเฉพาะรายการที่ส่งมา
    @Query("""
        select distinct be.equipment.equipmentId
        from BookingEquipment be
        join be.booking b
        where be.equipment.equipmentId in :equipmentIds
          and lower(coalesce(b.bookingstatus,'')) <> lower(:status)
    """)
    List<Integer> findEquipmentIdsByNotStatusAndIds(@Param("status") String status,
                                                    @Param("equipmentIds") List<Integer> equipmentIds);


}
