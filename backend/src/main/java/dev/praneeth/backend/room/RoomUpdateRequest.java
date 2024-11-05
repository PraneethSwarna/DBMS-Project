package dev.praneeth.backend.Room;

public class RoomUpdateRequest {
    private String roomNumber;
    private Room.RoomType roomType;
    private Room.Status status;

    // Constructors
    public RoomUpdateRequest() {}

    public RoomUpdateRequest(String roomNumber, Room.RoomType roomType, Room.Status status) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;
    }

    // Getters and Setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room.RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(Room.RoomType roomType) {
        this.roomType = roomType;
    }

    public Room.Status getStatus() {
        return status;
    }

    public void setStatus(Room.Status status) {
        this.status = status;
    }
}
