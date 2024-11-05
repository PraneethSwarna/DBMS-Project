package dev.praneeth.backend.Room;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Retrieve all rooms
    @GetMapping
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    // Retrieve room by room number
    @GetMapping(path = "/{roomNumber}")
    public Optional<Room> getRoomByRoomNumber(@PathVariable("roomNumber") String roomNumber) {
        return roomService.getRoomByRoomNumber(roomNumber);
    }

    @GetMapping(path = "/status/{status}/type/{roomType}")
    public List<Room> getRoomsByStatusAndType(@PathVariable("status") String status, @PathVariable("roomType") String roomType) {
        return roomService.getRoomsByStatusAndType(status, roomType);
    }

    // Add a new room
    @PostMapping
    public void addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
    }

    // Delete a room by ID
    @DeleteMapping(path = "/{roomNumber}")
    public void deleteRoom(@PathVariable("roomNumber") String roomNumber) {
        roomService.deleteRoom(roomNumber);
    }

    // Update a room by ID
    @PutMapping(path = "/{roomNumber}")
    public void updateRoom(@PathVariable("roomNumber") String roomNumber, @RequestBody RoomUpdateRequest updateRequest) {
        roomService.updateRoom(roomNumber, updateRequest);
    }
}
