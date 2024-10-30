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
    @DeleteMapping(path = "/{roomId}")
    public void deleteRoom(@PathVariable("roomId") Integer roomId) {
        roomService.deleteRoom(roomId);
    }

    // Update a room by ID
    @PutMapping(path = "/{roomId}")
    public void updateRoom(@PathVariable("roomId") Integer roomId, @RequestBody RoomUpdateRequest updateRequest) {
        roomService.updateRoom(roomId, updateRequest);
    }
}
