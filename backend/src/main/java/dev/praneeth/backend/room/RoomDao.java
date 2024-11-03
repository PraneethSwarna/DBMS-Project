package dev.praneeth.backend.room;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomDao {

    private final JdbcTemplate jdbcTemplate;

    public RoomDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map SQL result set to Room object
    @SuppressWarnings("unused")
    private RowMapper<Room> roomRowMapper() {
        return (rs, rowNum) -> {
            Room room = new Room();
            room.setRoomNumber(rs.getString("roomNumber"));
            room.setRoomType(Room.RoomType.valueOf(rs.getString("roomType")));
            room.setStatus(Room.Status.valueOf(rs.getString("status")));
            return room;
        };
    }

    // Retrieve all rooms
    public List<Room> getAllRooms() {
        String sql = "SELECT * FROM room";
        return jdbcTemplate.query(sql, roomRowMapper());
    }

    // Retrieve a room by room number
    public Optional<Room> getRoomByRoomNumber(String roomNumber) {
        String sql = "SELECT * FROM room WHERE roomNumber = ?";
        return jdbcTemplate.query(sql, roomRowMapper(), roomNumber).stream().findFirst();
    }

    // Add a new room
    public void addRoom(Room room) {
        String sql = "INSERT INTO room (roomNumber, roomType, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                room.getRoomNumber(),
                room.getRoomType().name(),
                room.getStatus().name());
    }

    // Delete a room by room number
    public void deleteRoom(String roomNumber) {
        String sql = "DELETE FROM room WHERE roomNumber = ?";
        jdbcTemplate.update(sql, roomNumber);
    }

    // Update a room
    public void updateRoom(Room room) {
        String sql = "UPDATE room SET roomType = ?, status = ? WHERE roomNumber = ?";
        jdbcTemplate.update(sql,
                room.getRoomType().name(),
                room.getStatus().name(),
                room.getRoomNumber());
    }
}
