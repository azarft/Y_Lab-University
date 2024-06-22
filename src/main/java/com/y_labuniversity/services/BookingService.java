package com.y_labuniversity.services;

import com.y_labuniversity.model.*;
import com.y_labuniversity.repositories.BookingRepository;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Service class responsible for managing bookings of workspaces and conference rooms.
 * Provides methods to book rooms, cancel bookings, display available rooms, and manage user bookings.
 */
@Getter
@Setter
public class BookingService {
    private final BookingRepository bookingRepository = new BookingRepository();
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser;

    /**
     * Displays available workspaces and conference rooms and allows the user to select a room to book.
     *
     * @param workspaces      List of available workspaces
     * @param conferenceRooms List of available conference rooms
     */
    public void showWorkspacesAndConferenceRooms(List<Workspace> workspaces, List<ConferenceRoom> conferenceRooms) {
        System.out.println("Available Workspaces:");
        for (int i = 0; i < workspaces.size(); i++) {
            System.out.println((i + 1) + ". " + workspaces.get(i).getName());
        }

        System.out.println("Available Conference Rooms:");
        for (int i = 0; i < conferenceRooms.size(); i++) {
            System.out.println((i + 1 + workspaces.size()) + ". " + conferenceRooms.get(i).getName());
        }

        int choice = getIntInput("Enter 0 to go back or select a room to book: ");

        if (choice == 0) {
            return; // Go back to the previous menu
        }

        if (choice > 0 && choice <= workspaces.size()) {
            Workspace selectedWorkspace = workspaces.get(choice - 1);
            bookRoom(selectedWorkspace);
        } else if (choice > workspaces.size() && choice <= workspaces.size() + conferenceRooms.size()) {
            ConferenceRoom selectedConferenceRoom = conferenceRooms.get(choice - workspaces.size() - 1);
            bookRoom(selectedConferenceRoom);
        } else {
            System.out.println("Invalid choice. Please try again.");
            showWorkspacesAndConferenceRooms(workspaces, conferenceRooms);
        }
    }

    /**
     * Initiates the booking process for the selected room (either a workspace or a conference room).
     *
     * @param room The selected room object to book
     */
    private void bookRoom(Object room) {
        LocalDate date = null;
        boolean validDate = false;

        while (!validDate) {
            String dateInput = getStringInput("Enter booking date (YYYY-MM-DD): ");

            try {
                date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date in YYYY-MM-DD format.");
            }
        }

        // Get free slots based on the type of room (Workspace or ConferenceRoom)
        List<Slot> freeSlots;
        if (room instanceof Workspace) {
            freeSlots = bookingRepository.getFreeSlots(date, (Workspace) room);
        } else if (room instanceof ConferenceRoom) {
            freeSlots = bookingRepository.getFreeSlots(date, (ConferenceRoom) room);
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

        if (slotChoice == 0) {
            return; // Cancel booking
        }

        if (slotChoice > 0 && slotChoice <= freeSlots.size()) {
            Slot selectedSlot = freeSlots.get(slotChoice - 1);
            LocalDateTime startTime = selectedSlot.getStartTime();
            LocalDateTime endTime = selectedSlot.getEndTime();

            boolean validTimes = false;
            while (!validTimes) {
                String startTimeInput = getStringInput("Enter booking start time (HH:mm): ");
                String endTimeInput = getStringInput("Enter booking end time (HH:mm): ");

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    startTime = LocalDateTime.of(date, LocalTime.parse(startTimeInput, formatter));
                    endTime = LocalDateTime.of(date, LocalTime.parse(endTimeInput, formatter));

                    if (startTime.isBefore(endTime) && startTime.isAfter(selectedSlot.getStartTime()) && endTime.isBefore(selectedSlot.getEndTime())) {
                        validTimes = true;
                    } else {
                        System.out.println("Invalid booking times. Please enter times within the selected slot.");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid time format. Please enter time in HH:mm format.");
                }
            }

            Booking booking = new Booking(bookingRepository.getNextBookingId(), currentUser, room, startTime, endTime);
            bookingRepository.createBooking(booking);

            // Modified output message
            String roomName = getRoomNameFromBooking(booking);
            System.out.println("Booking confirmed for " + roomName + " on " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                    " from " + startTime.toLocalTime() + " to " + endTime.toLocalTime());
        } else {
            System.out.println("Invalid choice. Please try again.");
            bookRoom(room);
        }
    }

    /**
     * Handles displaying user's bookings and allows cancellation of bookings.
     */
    public void handleBooked() {
        List<Booking> bookings = bookingRepository.getBookingsForUser(currentUser);

        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
            return;
        }

        System.out.println("Your Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            String roomName = getRoomNameFromBooking(booking);
            System.out.println((i + 1) + ". Room: " + roomName + ", Date: " + booking.getDate() +
                    ", Time: " + booking.getStartTime().toLocalTime() + " - " + booking.getEndTime().toLocalTime());
        }

        int choice = getIntInput("Select a booking to cancel (0 to go back): ");

        if (choice == 0) {
            return; // Go back
        }

        if (choice > 0 && choice <= bookings.size()) {
            Booking selectedBooking = bookings.get(choice - 1);
            cancelBooking(selectedBooking);
        } else {
            System.out.println("Invalid choice. Please try again.");
            handleBooked();
        }
    }

    /**
     * Retrieves the name of the room from a booking object.
     *
     * @param booking The booking object
     * @return The name of the room associated with the booking
     */
    public String getRoomNameFromBooking(Booking booking) {
        if (booking.getResource() instanceof Workspace) {
            return ((Workspace) booking.getResource()).getName();
        } else if (booking.getResource() instanceof ConferenceRoom) {
            return ((ConferenceRoom) booking.getResource()).getName();
        }
        return "Unknown Room";
    }

    /**
     * Cancels a specific booking if it is cancellable (booking end time is in the future).
     *
     * @param booking The booking object to cancel
     */
    private void cancelBooking(Booking booking) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (currentDateTime.isAfter(booking.getEndTime())) {
            System.out.println("Cannot cancel booking. Booking end time has already passed.");
        } else {
            bookingRepository.deleteBooking(booking.getId());
            System.out.println("Booking canceled successfully.");
        }
    }

    /**
     * Retrieves all bookings from the repository.
     *
     * @return List of all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.getAllBookings().stream().toList();
    }

    /**
     * Deletes a booking with the specified ID.
     *
     * @param id The ID of the booking to delete
     */
    public void deleteBooking(Long id) {
        bookingRepository.deleteBooking(id);
    }

    /**
     * Creates a new booking in the repository.
     *
     * @param booking The booking object to create
     */
    public void createBooking(Booking booking) {
        bookingRepository.createBooking(booking);
    }

    /**
     * Updates an existing booking in the repository.
     *
     * @param booking The updated booking object
     */
    public void updateBooking(Booking booking) {
        bookingRepository.updateBooking(booking);
    }

    /**
     * Helper method to read integer input from the user.
     *
     * @param prompt The prompt message to display
     * @return The integer entered by the user
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
     * Helper method to read string input from the user.
     *
     * @param prompt The prompt message to display
     * @return The string entered by the user
     */
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
