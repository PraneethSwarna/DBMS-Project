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

    // Retrieve all rooms
    public List<Room> getAllRooms() {
        String sql = "SELECT * FROM room";
        return jdbcTemplate.query(sql, roomRowMapper());
    }

    // Retrieve a room by ID
    public Optional<Room> getRoomById(Integer id) {
        String sql = "SELECT * FROM room WHERE roomID = ?";
        return jdbcTemplate.query(sql, roomRowMapper(), id)
                .stream().findFirst();
    }

    // Retrieve a room by room number
    public Optional<Room> getRoomByRoomNumber(String roomNumber) {
        String sql = "SELECT * FROM room WHERE roomNumber = ?";
        return jdbcTemplate.query(sql, roomRowMapper(), roomNumber)
                .stream().findFirst();
    }

    // Add a new room
    public int addRoom(Room room) {
        String sql = "INSERT INTO room (roomNumber, roomType, status) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                room.getRoomNumber(),
                room.getRoomType().name(),
                room.getStatus().name()
        );
    }

    // Delete a room by ID
    public int deleteRoom(Integer id) {
        String sql = "DELETE FROM room WHERE roomID = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Update an existing room
    public int updateRoom(Room room) {
        String sql = "UPDATE room SET roomNumber = ?, roomType = ?, status = ? WHERE roomID = ?";
        return jdbcTemplate.update(sql,
                room.getRoomNumber(),
                room.getRoomType().name(),
                room.getStatus().name(),
                room.getRoomID()
        );
    }

    // RowMapper to map SQL result set to Room object
    private RowMapper<Room> roomRowMapper() {
        return (rs, rowNum) -> {
            Room room = new Room();
            room.setRoomID(rs.getInt("roomID"));
            room.setRoomNumber(rs.getString("roomNumber"));
            room.setRoomType(Room.RoomType.valueOf(rs.getString("roomType")));
            room.setStatus(Room.Status.valueOf(rs.getString("status")));
            return room;
        };
    }
}
