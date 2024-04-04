package com.chandrashekhar.roomservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chandrashekhar.roomservice.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
    // @Query("SELECT r FROM Room r WHERE r.is_available = ?1")
    List<Room> findByIsAvailable(Boolean available);

    // @Query("SELECT r FROM Room r WHERE r. = ?1")
    List<Room> findByRoomType_Name(String roomTypeName);

    List<Room> findByRoomType_NameAndIsAvailable(String roomTypeName, Boolean available);
}
