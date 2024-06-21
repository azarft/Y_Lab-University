package com.y_labuniversity.repositories;

import com.y_labuniversity.model.Booking;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
}
