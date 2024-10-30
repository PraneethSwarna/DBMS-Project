package dev.praneeth.backend.room;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomConfig {

    /*@Bean
    CommandLineRunner commandLineRunner(RoomDao roomDao) {
        return args -> {
            Room room1 = new Room(null, "101", Room.RoomType.General, Room.Status.Available);
            Room room2 = new Room(null, "102", Room.RoomType.Private, Room.Status.Occupied);
            Room room3 = new Room(null, "103", Room.RoomType.ICU, Room.Status.Maintenance);

            // Insert the rooms into the database
            roomDao.addRoom(room1);
            roomDao.addRoom(room2);
            roomDao.addRoom(room3);
        };
    }*/
}
