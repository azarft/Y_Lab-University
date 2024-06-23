package com.y_labuniversity.repositories;

import com.y_labuniversity.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Repository class for managing bookings.
 * Provides methods for CRUD operations on booking data.
 */
public class BookingRepository {

    private Map<Long, Booking> bookings = new HashMap<>();

    /**
     * Retrieves all bookings in the repository.
     *
     * @return a collection of all bookings
     */
    public Collection<Booking> getAllBookings() {
        return bookings.values();
    }


    public List<Booking> getBookingsForUser(User user) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getUser().equals(user)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id the ID of the booking to retrieve
     * @return the booking with the specified ID
     * @throws IllegalArgumentException if the booking is not found
     */
    public Booking getBooking(long id) {
        if (!bookings.containsKey(id)) {
            throw new IllegalArgumentException("Booking with id " + id + " not found");
        }
        return bookings.get(id);
    }

    /**
     * Creates a new booking in the repository.
     *
     * @param booking the booking to create
     * @throws IllegalArgumentException if the booking already exists or if the time is not valid
     */
    public void createBooking(Booking booking) {
        if (bookings.containsKey(booking.getId())) {
            throw new IllegalArgumentException("Booking with id " + booking.getId() + " already exists");
        }
        if (isInPast(booking.getStartTime())) {
            throw new IllegalArgumentException("Booking start time cannot be in the past");
        }
        if (!isTimeValid(booking.getStartTime(), booking.getEndTime())) {
            throw new IllegalArgumentException("This time is not valid");
        }
        bookings.put(booking.getId(), booking);
    }

    /**
     * Updates an existing booking in the repository.
     *
     * @param booking the booking to update
     * @throws IllegalArgumentException if the booking is not found
     */
    public void updateBooking(Booking booking) {
        if (!bookings.containsKey(booking.getId())) {
            throw new IllegalArgumentException("Booking with id " + booking.getId() + " not found");
        }
        if (!isTimeValid(booking.getStartTime(), booking.getEndTime())) {
            throw new IllegalArgumentException("This time is not valid");
        }
        bookings.put(booking.getId(), booking);
    }

    /**
     * Checks if the given time range is valid and does not overlap with existing bookings.
     *
     * @param startTime the start time of the booking
     * @param endTime the end time of the booking
     * @return true if the time range is valid, false otherwise
     */
    public boolean isTimeValid(LocalDateTime startTime, LocalDateTime endTime) {
        for (Booking existingBooking : bookings.values()) {
            if (startTime.isBefore(existingBooking.getEndTime()) && endTime.isAfter(existingBooking.getStartTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a given LocalDateTime is before the current time.
     *
     * @param dateTime the LocalDateTime to check
     * @return true if dateTime is in the past, false otherwise
     * @throws NullPointerException if dateTime is null
     */
    public static boolean isInPast(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new NullPointerException("dateTime cannot be null");
        }
        return dateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Retrieves available slots for a given date and workspace.
     *
     * @param date      the date for which to retrieve available slots
     * @param workspace the workspace for which to retrieve available slots
     * @return a list of available slots for the specified date and workspace
     */
    public List<Slot> getFreeSlots(LocalDate date, Workspace workspace) {
        List<Slot> freeSlots = new ArrayList<>();

        // Define the start and end time for the day
        LocalDateTime startOfDay = date.atTime(LocalTime.of(0, 0));  // Assume booking starts at 9 AM
        LocalDateTime endOfDay = date.atTime(LocalTime.of(23, 59));   // Assume booking ends at 6 PM

        // Initialize the current start time to the beginning of the day
        LocalDateTime currentStartTime = startOfDay;

        // Sort existing bookings for the given date and workspace
        List<Booking> bookingsForDate = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getStartTime().toLocalDate().equals(date) && booking.getResource() instanceof Workspace &&
                    ((Workspace) booking.getResource()).equals(workspace)) {
                bookingsForDate.add(booking);
            }
        }
        bookingsForDate.sort((b1, b2) -> b1.getStartTime().compareTo(b2.getStartTime()));

        // Iterate through the sorted bookings to find free slots
        for (Booking booking : bookingsForDate) {
            LocalDateTime bookingStartTime = booking.getStartTime();
            if (currentStartTime.isBefore(bookingStartTime)) {
                freeSlots.add(new Slot(currentStartTime, bookingStartTime));
            }
            currentStartTime = booking.getEndTime();
        }

        // Check for a free slot between the last booking end time and the end of the day
        if (currentStartTime.isBefore(endOfDay)) {
            freeSlots.add(new Slot(currentStartTime, endOfDay));
        }

        return freeSlots;
    }

    /**
     * Retrieves available slots for a given date and conference room.
     *
     * @param date           the date for which to retrieve available slots
     * @param conferenceRoom the conference room for which to retrieve available slots
     * @return a list of available slots for the specified date and conference room
     */
    public List<Slot> getFreeSlots(LocalDate date, ConferenceRoom conferenceRoom) {
        List<Slot> freeSlots = new ArrayList<>();

        // Define the start and end time for the day
        LocalDateTime startOfDay = date.atTime(LocalTime.of(9, 0));  // Assume booking starts at 9 AM
        LocalDateTime endOfDay = date.atTime(LocalTime.of(18, 0));   // Assume booking ends at 6 PM

        // Initialize the current start time to the beginning of the day
        LocalDateTime currentStartTime = startOfDay;

        // Sort existing bookings for the given date and conference room
        List<Booking> bookingsForDate = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getStartTime().toLocalDate().equals(date) && booking.getResource() instanceof ConferenceRoom &&
                    ((ConferenceRoom) booking.getResource()).equals(conferenceRoom)) {
                bookingsForDate.add(booking);
            }
        }
        bookingsForDate.sort((b1, b2) -> b1.getStartTime().compareTo(b2.getStartTime()));

        // Iterate through the sorted bookings to find free slots
        for (Booking booking : bookingsForDate) {
            LocalDateTime bookingStartTime = booking.getStartTime();
            if (currentStartTime.isBefore(bookingStartTime)) {
                freeSlots.add(new Slot(currentStartTime, bookingStartTime));
            }
            currentStartTime = booking.getEndTime();
        }

        // Check for a free slot between the last booking end time and the end of the day
        if (currentStartTime.isBefore(endOfDay)) {
            freeSlots.add(new Slot(currentStartTime, endOfDay));
        }

        return freeSlots;
    }



    /**
     * Gets the next available booking ID.
     *
     * @return the next available booking ID
     */
    public long getNextBookingId() {
        return bookings.isEmpty() ? 1 : Collections.max(bookings.keySet()) + 1;
    }

    public void deleteBooking(long id) {
        if (!bookings.containsKey(id)) {
            throw new IllegalArgumentException("Booking with id " + id + " not found");
        }
        bookings.remove(id);
    }
}
