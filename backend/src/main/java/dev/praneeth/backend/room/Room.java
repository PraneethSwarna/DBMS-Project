package dev.praneeth.backend.room;

public class Room {
    private String roomNumber; // Primary Key
    private RoomType roomType;
    private Status status;

    // Enum for room type
    public enum RoomType {
        General,
        Private,
        ICU
    }

    // Enum for room status
    public enum Status {
        Available,
        Occupied,
        Maintenance
    }

    // Constructors
    public Room() {}

    public Room(String roomNumber, RoomType roomType, Status status) {
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

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", roomType=" + roomType +
                ", status=" + status +
                '}';
    }
}
