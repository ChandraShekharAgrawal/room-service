package com.chandrashekhar.roomservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chandrashekhar.roomservice.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

}
