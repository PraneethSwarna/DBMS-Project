package dev.praneeth.backend.Room;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomDao roomDao;

    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    // Retrieve all rooms
    public List<Room> getRooms() {
        return roomDao.getAllRooms();
    }

    // Retrieve room by room number
    public Optional<Room> getRoomByRoomNumber(String roomNumber) {
        return roomDao.getRoomByRoomNumber(roomNumber);
    }

    // Retrieve rooms by status and type
    public List<Room> getRoomsByStatusAndType(String status, String roomType) {
        return roomDao.getRoomsByStatusAndType(status, roomType);
    }

    // Add a new room
    public void addRoom(Room room) {
        Optional<Room> existingRoom = roomDao.getRoomByRoomNumber(room.getRoomNumber());
        if (existingRoom.isPresent()) {
            throw new IllegalStateException("Room number already exists");
        }
        roomDao.addRoom(room);
    }

    // Delete a room by room number
    public void deleteRoom(String roomNumber) {
        boolean exists = roomDao.getRoomByRoomNumber(roomNumber).isPresent();
        if (!exists) {
            throw new IllegalStateException("Room with number " + roomNumber + " does not exist.");
        }
        roomDao.deleteRoom(roomNumber);
    }

    // Update a room by room number
    @Transactional
    public void updateRoom(String roomNumber, RoomUpdateRequest updateRequest) {
        // Retrieve the room or throw an exception if not found
        Room room = roomDao.getRoomByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalStateException("Room with number " + roomNumber + " does not exist."));

        // Update room type if provided
        if (updateRequest.getRoomType() != null) {
            room.setRoomType(updateRequest.getRoomType());
        }

        // Update status if provided
        if (updateRequest.getStatus() != null) {
            room.setStatus(updateRequest.getStatus());
        }

        // Save the updated room
        roomDao.updateRoom(room);
    }
}
