package dev.praneeth.backend.room;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping(path = "/{roomId}")
    public void updateRoom(@PathVariable("roomNumber") String roomNumber, @RequestBody RoomUpdateRequest updateRequest) {
        roomService.updateRoom(roomNumber, updateRequest);
    }
}
