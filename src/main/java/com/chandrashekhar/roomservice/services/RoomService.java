package com.chandrashekhar.roomservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chandrashekhar.roomservice.entities.Room;
import com.chandrashekhar.roomservice.entities.RoomDto;
import com.chandrashekhar.roomservice.entities.RoomType;
import com.chandrashekhar.roomservice.mappers.RoomMapper;
import com.chandrashekhar.roomservice.repositories.RoomRepository;
import com.chandrashekhar.roomservice.repositories.RoomTypeRepository;

import jakarta.transaction.Transactional;
// import lombok.AllArgsConstructor;
// import lombok.NoArgsConstructor;

@Service
// @NoArgsConstructor
// @AllArgsConstructor
public class RoomService {

    private RoomRepository roomRepository;
    private RoomMapper roomMapper;
    private RoomTypeRepository roomTypeRepository;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper, RoomTypeRepository roomTypeRepository){
        this.roomMapper = roomMapper;
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    // public RoomService(RoomRepository roomRepository){
    //     this.roomRepository = roomRepository;
    // }

    // public RoomService(RoomMapper roomMapper){
    //     this.roomMapper = roomMapper;
    // }

    // public RoomService(RoomTypeRepository roomTypeRepository){
    //     this.roomTypeRepository = roomTypeRepository;
    // }

    public List<RoomDto> getAllRooms(String roomType, Boolean available){

        List<Room> rooms;
        if(roomType == null && available == null){
            rooms = roomRepository.findAll();
        }
        else if(roomType == null && available != null){
            rooms = roomRepository.findByIsAvailable(available);
        }
        else if(roomType != null && available == null){
            rooms = roomRepository.findByRoomType_Name(roomType);
        }
        else{
            rooms = roomRepository.findByRoomType_NameAndIsAvailable(roomType, available);
        }
        return roomMapper.convertToDtoList(rooms);
    }

    public RoomDto getRoomById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isPresent()){
            return roomMapper.convertToDto(roomOptional.get());
        }
        return null;
    }

    @Transactional
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = roomMapper.convertDtoToRoom(roomDto);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.convertToDto(savedRoom);
    }

    @Transactional
    public RoomDto updateRoom(Long id, RoomDto roomDto) {
        Optional<Room> existingRoomOptional = roomRepository.findById(id);
        Room existingRoom = null;
        if(existingRoomOptional.isPresent()){
            existingRoom = existingRoomOptional.get();
        }

        existingRoom.setRoomNumber(roomDto.getRoomNumber());

        Optional<RoomType> roomTypeOptionalToUpdate = roomTypeRepository.findById(roomDto.getRoomTypeId());
        RoomType roomTypeToUpdate = null;
        if(roomTypeOptionalToUpdate.isPresent()){
            roomTypeToUpdate = roomTypeOptionalToUpdate.get();
        }
        existingRoom.setRoomType(roomTypeToUpdate);

        Room updatedRoom = roomRepository.save(existingRoom);

        return roomMapper.convertToDto(updatedRoom);
    }

    @Transactional
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
