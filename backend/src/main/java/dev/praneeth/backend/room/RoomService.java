package dev.praneeth.backend.room;

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

    // Add a new room
    public void addRoom(Room room) {
        Optional<Room> existingRoom = roomDao.getRoomByRoomNumber(room.getRoomNumber());
        if (existingRoom.isPresent()) {
            throw new IllegalStateException("Room number already exists");
        }
        roomDao.addRoom(room);
    }

    // Delete a room by ID
    public void deleteRoom(Integer roomId) {
        boolean exists = roomDao.getRoomById(roomId).isPresent();
        if (!exists) {
            throw new IllegalStateException("Room with ID " + roomId + " does not exist.");
        }
        roomDao.deleteRoom(roomId);
    }

    // Update a room by ID
    @Transactional
    public void updateRoom(Integer roomId, RoomUpdateRequest updateRequest) {
        // Retrieve the room or throw an exception if not found
        Room room = roomDao.getRoomById(roomId)
                .orElseThrow(() -> new IllegalStateException("Room with ID " + roomId + " does not exist."));

        // Update room number if provided and not empty
        if (updateRequest.getRoomNumber() != null && !updateRequest.getRoomNumber().trim().isEmpty()) {
            // Check if the new room number is already taken by another room
            Optional<Room> roomWithNumber = roomDao.getRoomByRoomNumber(updateRequest.getRoomNumber());
            if (roomWithNumber.isPresent() && !roomWithNumber.get().getRoomID().equals(roomId)) {
                throw new IllegalStateException("Room number already taken");
            }
            room.setRoomNumber(updateRequest.getRoomNumber());
        }

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
