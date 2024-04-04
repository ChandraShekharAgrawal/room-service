package com.chandrashekhar.roomservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chandrashekhar.roomservice.entities.RoomType;


@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long>{

}
