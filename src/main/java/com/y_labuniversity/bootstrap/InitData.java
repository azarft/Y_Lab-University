package com.y_labuniversity.bootstrap;

import com.y_labuniversity.model.Booking;
import com.y_labuniversity.model.ConferenceRoom;
import com.y_labuniversity.model.User;
import com.y_labuniversity.model.Workspace;
import com.y_labuniversity.services.BookingService;
import com.y_labuniversity.services.ConferenceRoomService;
import com.y_labuniversity.services.UserService;
import com.y_labuniversity.services.WorkspaceService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for initializing test data for the application.
 */
public class InitData {

    /**
     * Initializes test data for users, workspaces, conference rooms, and bookings.
     *
     * @param userService         Service for managing user-related operations.
     * @param workspaceService    Service for managing workspace-related operations.
     * @param conferenceRoomService Service for managing conference room-related operations.
     * @param bookingService      Service for managing booking-related operations.
     */
    public void initData(UserService userService, WorkspaceService workspaceService,
                         ConferenceRoomService conferenceRoomService, BookingService bookingService) {
        // Add admin user
        User admin = User.builder()
                .fullName("Admin of Company")
                .username("admin")
                .password("admin")
                .build();
        userService.register(admin);

        // Add 10 users
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = User.builder()
                    .fullName("User " + i)
                    .username("user" + i)
                    .password("password" + i)
                    .build();
            userService.register(user);
            users.add(user);
        }

        // Add 10 workspaces
        List<Workspace> workspaces = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Workspace workspace = Workspace.builder()
                    .id((long) i)
                    .name("Workspace " + i)
                    .capacity(5 + i)  // Example capacity
                    .build();
            workspaceService.createWorkspace(workspace);
            workspaces.add(workspace);
        }

        // Add 10 conference rooms
        List<ConferenceRoom> conferenceRooms = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ConferenceRoom conferenceRoom = ConferenceRoom.builder()
                    .id((long) i)
                    .name("Conference Room " + i)
                    .capacity(10 + i)  // Example capacity
                    .build();
            conferenceRoomService.createConferenceRoom(conferenceRoom);
            conferenceRooms.add(conferenceRoom);
        }

        // Book each workspace for the respective user
        for (int i = 1; i <= 10; i++) {
            Booking booking = Booking.builder()
                    .id((long) i)
                    .user(users.get(i - 1))
                    .resource(workspaces.get(i - 1))
                    .startTime(LocalDateTime.now().plusDays(i))
                    .endTime(LocalDateTime.now().plusDays(i).plusHours(3))
                    .build();
            bookingService.createBooking(booking);
        }

        // Book each conference room for users in a cyclical manner
        for (int i = 11; i <= 20; i++) {
            Booking booking = Booking.builder()
                    .id((long) i)
                    .user(users.get((i - 1) % 10))  // Cycle through users
                    .resource(conferenceRooms.get((i - 1) % 10))  // Cycle through conference rooms
                    .startTime(LocalDateTime.now().plusDays(i))
                    .endTime(LocalDateTime.now().plusDays(i).plusHours(3))
                    .build();
            bookingService.createBooking(booking);
        }
    }
}
