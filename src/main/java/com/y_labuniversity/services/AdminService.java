package com.y_labuniversity.services;

import com.y_labuniversity.model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Service class for managing administrative tasks such as managing workspaces, conference rooms, bookings, and users.
 */
@Getter
@Setter
public class AdminService {
    private final Scanner scanner = new Scanner(System.in);
    private WorkspaceService workspaceService;
    private ConferenceRoomService conferenceRoomService;
    private UserService userService;

    /**
     * Handles the management of workspaces and conference rooms.
     *
     * @param workspaceService       the workspace service
     * @param conferenceRoomService  the conference room service
     */
    public void handleManageWorkspacesAndConferenceRooms(WorkspaceService workspaceService, ConferenceRoomService conferenceRoomService) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Manage Workspaces and Conference Rooms");
            System.out.println("1. Add Workspace");
            System.out.println("2. Edit Workspace");
            System.out.println("3. Delete Workspace");
            System.out.println("4. Add Conference Room");
            System.out.println("5. Edit Conference Room");
            System.out.println("6. Delete Conference Room");
            System.out.println("7. Back");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addWorkspace(workspaceService);
                    break;
                case 2:
                    editWorkspace(workspaceService);
                    break;
                case 3:
                    deleteWorkspace(workspaceService);
                    break;
                case 4:
                    addConferenceRoom(conferenceRoomService);
                    break;
                case 5:
                    editConferenceRoom(conferenceRoomService);
                    break;
                case 6:
                    deleteConferenceRoom(conferenceRoomService);
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Adds a new workspace.
     *
     * @param workspaceService the workspace service
     */
    private void addWorkspace(WorkspaceService workspaceService) {
        Long id = getLongInput("Enter Workspace ID: ");
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Workspace Name: ");
        String name = scanner.nextLine();

        int capacity = getIntInput("Enter Workspace Capacity: ");
        scanner.nextLine(); // Consume newline

        Workspace workspace = Workspace.builder()
                .id(id)
                .name(name)
                .capacity(capacity)
                .build();

        try {
            workspaceService.createWorkspace(workspace);
            System.out.println("Workspace added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Edits an existing workspace.
     *
     * @param workspaceService the workspace service
     */
    private void editWorkspace(WorkspaceService workspaceService) {
        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        if (workspaces.isEmpty()) {
            System.out.println("No workspaces available to edit.");
            return;
        }

        System.out.println("Select a Workspace to edit (0 to cancel):");
        for (int i = 0; i < workspaces.size(); i++) {
            System.out.println((i + 1) + ". " + workspaces.get(i).getName());
        }

        int choice = getIntInput("Enter your choice: ");
        scanner.nextLine(); // Consume newline

        if (choice == 0) return;
        if (choice < 1 || choice > workspaces.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        Workspace workspace = workspaces.get(choice - 1);

        System.out.print("Enter new Workspace Name (current: " + workspace.getName() + "): ");
        String name = scanner.nextLine();

        int capacity = getIntInput("Enter new Workspace Capacity (current: " + workspace.getCapacity() + "): ");
        scanner.nextLine(); // Consume newline

        workspace.setName(name);
        workspace.setCapacity(capacity);

        try {
            workspaceService.updateWorkspace(workspace);
            System.out.println("Workspace updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes an existing workspace.
     *
     * @param workspaceService the workspace service
     */
    private void deleteWorkspace(WorkspaceService workspaceService) {
        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        if (workspaces.isEmpty()) {
            System.out.println("No workspaces available to delete.");
            return;
        }

        System.out.println("Select a Workspace to delete (0 to cancel):");
        for (int i = 0; i < workspaces.size(); i++) {
            System.out.println((i + 1) + ". " + workspaces.get(i).getName());
        }

        int choice = getIntInput("Enter your choice: ");
        scanner.nextLine(); // Consume newline

        if (choice == 0) return;
        if (choice < 1 || choice > workspaces.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        Workspace workspace = workspaces.get(choice - 1);

        try {
            workspaceService.deleteWorkspace(workspace.getId());
            System.out.println("Workspace deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new conference room.
     *
     * @param conferenceRoomService the conference room service
     */
    private void addConferenceRoom(ConferenceRoomService conferenceRoomService) {
        Long id = getLongInput("Enter Conference Room ID: ");
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Conference Room Name: ");
        String name = scanner.nextLine();

        int capacity = getIntInput("Enter Conference Room Capacity: ");
        scanner.nextLine(); // Consume newline

        ConferenceRoom conferenceRoom = ConferenceRoom.builder()
                .id(id)
                .name(name)
                .capacity(capacity)
                .build();

        try {
            conferenceRoomService.createConferenceRoom(conferenceRoom);
            System.out.println("Conference room added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Edits an existing conference room.
     *
     * @param conferenceRoomService the conference room service
     */
    private void editConferenceRoom(ConferenceRoomService conferenceRoomService) {
        List<ConferenceRoom> conferenceRooms = conferenceRoomService.getAllConferenceRooms();
        if (conferenceRooms.isEmpty()) {
            System.out.println("No conference rooms available to edit.");
            return;
        }

        System.out.println("Select a Conference Room to edit (0 to cancel):");
        for (int i = 0; i < conferenceRooms.size(); i++) {
            System.out.println((i + 1) + ". " + conferenceRooms.get(i).getName());
        }

        int choice = getIntInput("Enter your choice: ");
        scanner.nextLine(); // Consume newline

        if (choice == 0) return;
        if (choice < 1 || choice > conferenceRooms.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        ConferenceRoom conferenceRoom = conferenceRooms.get(choice - 1);

        System.out.print("Enter new Conference Room Name (current: " + conferenceRoom.getName() + "): ");
        String name = scanner.nextLine();

        int capacity = getIntInput("Enter new Conference Room Capacity (current: " + conferenceRoom.getCapacity() + "): ");
        scanner.nextLine(); // Consume newline

        conferenceRoom.setName(name);
        conferenceRoom.setCapacity(capacity);

        try {
            conferenceRoomService.updateConferenceRoom(conferenceRoom);
            System.out.println("Conference room updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes an existing conference room.
     *
     * @param conferenceRoomService the conference room service
     */
    private void deleteConferenceRoom(ConferenceRoomService conferenceRoomService) {
        List<ConferenceRoom> conferenceRooms = conferenceRoomService.getAllConferenceRooms();
        if (conferenceRooms.isEmpty()) {
            System.out.println("No conference rooms available to delete.");
            return;
        }

        System.out.println("Select a Conference Room to delete (0 to cancel):");
        for (int i = 0; i < conferenceRooms.size(); i++) {
            System.out.println((i + 1) + ". " + conferenceRooms.get(i).getName());
        }

        int choice = getIntInput("Enter your choice: ");
        scanner.nextLine(); // Consume newline

        if (choice == 0) return;
        if (choice < 1 || choice > conferenceRooms.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        ConferenceRoom conferenceRoom = conferenceRooms.get(choice - 1);

        try {
            conferenceRoomService.deleteConferenceRoom(conferenceRoom.getId());
            System.out.println("Conference room deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles the management of bookings.
     *
     * @param bookingService         the booking service
     * @param workspaceService       the workspace service
     * @param conferenceRoomService  the conference room service
     */
    public void handleManageBookings(BookingService bookingService, WorkspaceService workspaceService, ConferenceRoomService conferenceRoomService) {
        this.workspaceService = workspaceService;
        this.conferenceRoomService = conferenceRoomService;
        while (true) {
            System.out.println("Manage Bookings:");
            System.out.println("1. Add Booking");
            System.out.println("2. Delete Booking");
            System.out.println("3. Back");

            int choice = getIntInput("Enter your choice: ");
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleAddBooking(bookingService);
                    break;
                case 2:
                    handleDeleteBooking(bookingService);
                    break;
                case 3:
                    return; // Go back to the previous menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Handles adding a new booking.
     *
     * @param bookingService the booking service
     */
    private void handleAddBooking(BookingService bookingService) {
        System.out.print("Enter username for booking: ");
        String username = scanner.nextLine();
        User user = new User(); // Replace with actual user retrieval logic
        user.setUsername(username);

        System.out.println("Select resource type:");
        System.out.println("1. Workspace");
        System.out.println("2. Conference Room");
        int resourceTypeChoice = getIntInput("Enter choice (1/2): ");
        scanner.nextLine(); // Consume newline

        if (resourceTypeChoice == 1) {
            List<Workspace> workspaces = workspaceService.getAllWorkspaces(); // Replace with actual logic to get workspaces
            System.out.println("Available Workspaces:");
            for (int i = 0; i < workspaces.size(); i++) {
                System.out.println((i + 1) + ". " + workspaces.get(i).getName());
            }
            int workspaceChoice = getIntInput("Select a workspace to book: ");
            scanner.nextLine(); // Consume newline

            if (workspaceChoice > 0 && workspaceChoice <= workspaces.size()) {
                Workspace selectedWorkspace = workspaces.get(workspaceChoice - 1);
                bookRoom(bookingService, user, selectedWorkspace);
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (resourceTypeChoice == 2) {
            List<ConferenceRoom> conferenceRooms = conferenceRoomService.getAllConferenceRooms(); // Replace with actual logic to get conference rooms
            System.out.println("Available Conference Rooms:");
            for (int i = 0; i < conferenceRooms.size(); i++) {
                System.out.println((i + 1) + ". " + conferenceRooms.get(i).getName());
            }
            int conferenceRoomChoice = getIntInput("Select a conference room to book: ");
            scanner.nextLine(); // Consume newline

            if (conferenceRoomChoice > 0 && conferenceRoomChoice <= conferenceRooms.size()) {
                ConferenceRoom selectedConferenceRoom = conferenceRooms.get(conferenceRoomChoice - 1);
                bookRoom(bookingService, user, selectedConferenceRoom);
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Invalid resource type.");
        }
    }

    /**
     * Handles deleting an existing booking.
     *
     * @param bookingService the booking service
     */
    private void handleDeleteBooking(BookingService bookingService) {
        List<Booking> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings available to delete.");
            return;
        }

        System.out.println("Available Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            System.out.println((i + 1) + ". Booking ID: " + booking.getId() +
                    ", User: " + booking.getUser().getUsername() +
                    ", Room: " + bookingService.getRoomNameFromBooking(booking) +
                    ", Start: " + booking.getStartTime() +
                    ", End: " + booking.getEndTime());
        }

        int bookingChoice = getIntInput("Select a booking to delete (0 to cancel): ");
        scanner.nextLine(); // Consume newline

        if (bookingChoice == 0) {
            return; // Cancel deletion
        }

        if (bookingChoice > 0 && bookingChoice <= bookings.size()) {
            Booking selectedBooking = bookings.get(bookingChoice - 1);
            try {
                bookingService.deleteBooking(selectedBooking.getId());
                System.out.println("Booking deleted successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /**
     * Books a room (workspace or conference room) for a user.
     *
     * @param bookingService the booking service
     * @param user           the user
     * @param room           the room (workspace or conference room)
     */
    private void bookRoom(BookingService bookingService, User user, Object room) {
        LocalDate date = null;
        boolean validDate = false;

        while (!validDate) {
            System.out.print("Enter booking date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();

            try {
                date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
                validDate = true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter a valid date in YYYY-MM-DD format.");
            }
        }

        // Get free slots based on the type of room (Workspace or ConferenceRoom)
        List<Slot> freeSlots;
        if (room instanceof Workspace) {
            freeSlots = bookingService.getBookingRepository().getFreeSlots(date, (Workspace) room);
        } else if (room instanceof ConferenceRoom) {
            freeSlots = bookingService.getBookingRepository().getFreeSlots(date, (ConferenceRoom) room);
        } else {
            System.out.println("Invalid room type.");
            return;
        }

        if (freeSlots.isEmpty()) {
            System.out.println("No available slots for the selected date and room.");
            return;
        }

        System.out.println("Available slots:");
        for (int i = 0; i < freeSlots.size(); i++) {
            Slot slot = freeSlots.get(i);
            System.out.println((i + 1) + ". " + slot.getStartTime().toLocalTime() + " to " + slot.getEndTime().toLocalTime());
        }

        int slotChoice = getIntInput("Select a slot to book (0 to cancel): ");
        scanner.nextLine(); // Consume newline

        if (slotChoice == 0) {
            return; // Cancel booking
        }

        if (slotChoice > 0 && slotChoice <= freeSlots.size()) {
            Slot selectedSlot = freeSlots.get(slotChoice - 1);
            LocalDateTime startTime = selectedSlot.getStartTime();
            LocalDateTime endTime = selectedSlot.getEndTime();

            boolean validTimes = false;
            while (!validTimes) {
                System.out.print("Enter booking start time (HH:mm): ");
                String startTimeInput = scanner.nextLine();
                System.out.print("Enter booking end time (HH:mm): ");
                String endTimeInput = scanner.nextLine();

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    startTime = LocalDateTime.of(date, LocalTime.parse(startTimeInput, formatter));
                    endTime = LocalDateTime.of(date, LocalTime.parse(endTimeInput, formatter));

                    if (startTime.isBefore(endTime) && startTime.isAfter(selectedSlot.getStartTime()) && endTime.isBefore(selectedSlot.getEndTime())) {
                        validTimes = true;
                    } else {
                        System.out.println("Invalid booking times. Please enter times within the selected slot.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid time format. Please enter time in HH:mm format.");
                }
            }

            Booking booking = new Booking(bookingService.getBookingRepository().getNextBookingId(), user, room, startTime, endTime);
            bookingService.createBooking(booking);

            // Modified output message
            String roomName = bookingService.getRoomNameFromBooking(booking);
            System.out.println("Booking confirmed for " + roomName + " on " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                    " from " + startTime.toLocalTime() + " to " + endTime.toLocalTime());
        } else {
            System.out.println("Invalid choice. Please try again.");
            bookRoom(bookingService, user, room);
        }
    }

    /**
     * Handles the management of users.
     *
     * @param userService the user service
     */
    public void handleManageUsers(UserService userService) {
        this.userService = userService;
        while (true) {
            System.out.println("----- Manage Users -----");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Edit User");
            System.out.println("4. Back");

            int choice = getIntInput("Enter your choice: ");
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    editUser();
                    break;
                case 4:
                    return; // Go back
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    /**
     * Adds a new user.
     */
    private void addUser() {
        System.out.println("----- Add User -----");

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User newUser = User.builder()
                .fullName(fullName)
                .username(username)
                .password(password)
                .build();

        try {
            userService.register(newUser);
            System.out.println("User added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes an existing user.
     */
    private void deleteUser() {
        System.out.println("----- Delete User -----");

        displayAllUsers();

        System.out.print("Enter username of the user to delete: ");
        String username = scanner.nextLine();

        try {
            userService.getUserRepository().deleteUser(username);
            System.out.println("User deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Edits an existing user.
     */
    private void editUser() {
        System.out.println("----- Edit User -----");

        displayAllUsers();

        System.out.print("Enter username of the user to edit: ");
        String username = scanner.nextLine();

        User user = userService.getUserRepository().getUserByUsername(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Current Details:");
        System.out.println("Full Name: " + user.getFullName());
        System.out.println("Username: " + user.getUsername());

        System.out.print("Enter new full name (leave blank to keep current): ");
        String newFullName = scanner.nextLine().trim();

        System.out.print("Enter new username (leave blank to keep current): ");
        String newUsername = scanner.nextLine().trim();

        if (!newFullName.isEmpty()) {
            user.setFullName(newFullName);
        }
        if (!newUsername.isEmpty()) {
            user.setUsername(newUsername);
        }

        try {
            userService.getUserRepository().updateUser(username, user);
            System.out.println("User updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all users.
     */
    private void displayAllUsers() {
        Collection<User> users = userService.getUserRepository().getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("----- All Users -----");
        for (User user : users) {
            System.out.println("Username: " + user.getUsername() + ", Full Name: " + user.getFullName());
        }
    }

    /**
     * Helper method to get integer input from the user.
     *
     * @param prompt the prompt message
     * @return the integer input
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume invalid input
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }

    /**
     * Helper method to get long input from the user.
     *
     * @param prompt the prompt message
     * @return the long input
     */
    private long getLongInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return scanner.nextLong();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume invalid input
                System.out.print("Invalid input. Please enter a long integer: ");
            }
        }
    }
}
