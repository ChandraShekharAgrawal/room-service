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
        throw new IllegalArgumentException("Room not found with id: " + id);
    }

    @Transactional
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = roomMapper.convertDtoToRoom(roomDto);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.convertToDto(savedRoom);
    }

    @Transactional
    public RoomDto updateRoom(Boolean checkin, Long id, RoomDto roomDto) {
        
        Optional<Room> existingRoomOptional = roomRepository.findById(id);

        if(existingRoomOptional.isPresent()){
            Room existingRoom = existingRoomOptional.get();

            if (checkin != null) {
                if (checkin) {
                    if (existingRoom.getIsAvailable()) {
                        // Room is available, perform check-in
                        existingRoom.setIsAvailable(false);
                    }
                    else {
                        // Room is already occupied, throw an exception
                        throw new IllegalStateException("Room is already occupied");
                    }
                }
                else {
                    // Perform check-out
                    existingRoom.setIsAvailable(true);
                }
            }
            else {
                // Update room details if not performing check-in or check-out
                existingRoom.setRoomNumber(roomDto.getRoomNumber());
                Optional<RoomType> roomTypeOptionalToUpdate = roomTypeRepository.findById(roomDto.getRoomTypeId());
                RoomType roomTypeToUpdate = roomTypeOptionalToUpdate.orElse(null);
                existingRoom.setRoomType(roomTypeToUpdate);
            }

            Room updatedRoom = roomRepository.save(existingRoom);
            return roomMapper.convertToDto(updatedRoom);
        }
        else {
            // Handle case when room with given id is not found by throwing an exception
            throw new IllegalArgumentException("Room not found with id: " + id);
        }
    }

    @Transactional
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
