package com.chandrashekhar.roomservice.mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.chandrashekhar.roomservice.entities.Room;
import com.chandrashekhar.roomservice.entities.RoomDto;
import com.chandrashekhar.roomservice.entities.RoomType;
import com.chandrashekhar.roomservice.repositories.RoomTypeRepository;

@Component
public class RoomMapper {

    private RoomTypeRepository roomTypeRepository;

    public RoomMapper(RoomTypeRepository roomTypeRepository){
        this.roomTypeRepository = roomTypeRepository;
    }

    public RoomDto convertToDto(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomTypeId(room.getRoomType().getId());
        return dto;
    }

    public Room convertDtoToRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setId(roomDto.getId());
        room.setRoomNumber(roomDto.getRoomNumber());
        Optional<RoomType> roomType = roomTypeRepository.findById(roomDto.getRoomTypeId());
        if(roomType.isPresent()){
            room.setRoomType(roomType.get());
        }
        return room;
    }

    public List<RoomDto> convertToDtoList(List<Room> rooms) {
        return rooms.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
    }
}

