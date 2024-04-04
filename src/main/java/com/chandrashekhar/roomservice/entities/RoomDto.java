package com.chandrashekhar.roomservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Long id;
    private String roomNumber;
    private Long roomTypeId;   
    private Boolean isAvailable;

    // Override toString() method for better logging and debugging
    @Override
    public String toString() {
        return "RoomDTO{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomTypeId=" + roomTypeId +
                ", isAvailable=" + isAvailable +
                '}';
    }   
}
