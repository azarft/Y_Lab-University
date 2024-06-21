package RepositoryTest;

import com.y_labuniversity.model.Booking;
import com.y_labuniversity.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingRepositoryTest {

    @Mock
    private Map<Long, Booking> bookingsMock;

    @InjectMocks
    private BookingRepository bookingRepository = new BookingRepository();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBookings() {
        Booking booking1 = Booking.builder().id(1L).startTime(LocalDateTime.now().plusDays(1)).endTime(LocalDateTime.now().plusDays(1).plusHours(1)).build();
        Booking booking2 = Booking.builder().id(2L).startTime(LocalDateTime.now().plusDays(2)).endTime(LocalDateTime.now().plusDays(2).plusHours(1)).build();

        when(bookingsMock.values()).thenReturn(List.of(booking1, booking2));

        Collection<Booking> allBookings = bookingRepository.getAllBookings();
        assertNotNull(allBookings);
        assertEquals(2, allBookings.size());
        assertTrue(allBookings.contains(booking1));
        assertTrue(allBookings.contains(booking2));
    }

    @Test
    void testGetBooking() {
        Booking booking = Booking.builder().id(1L).startTime(LocalDateTime.now().plusDays(1)).endTime(LocalDateTime.now().plusDays(1).plusHours(1)).build();

        when(bookingsMock.containsKey(1L)).thenReturn(true);
        when(bookingsMock.get(1L)).thenReturn(booking);

        Booking retrievedBooking = bookingRepository.getBooking(1L);
        assertNotNull(retrievedBooking);
        assertEquals(1L, retrievedBooking.getId());
    }

    @Test
    void testCreateBooking() {
        Booking booking = Booking.builder().id(1L).startTime(LocalDateTime.now().plusDays(1)).endTime(LocalDateTime.now().plusDays(1).plusHours(1)).build();

        when(bookingsMock.containsKey(1L)).thenReturn(false);

        bookingRepository.createBooking(booking);
        verify(bookingsMock, times(1)).put(1L, booking);
    }

    @Test
    void testUpdateBooking() {
        Booking booking = Booking.builder().id(1L).startTime(LocalDateTime.now().plusDays(1)).endTime(LocalDateTime.now().plusDays(1).plusHours(1)).build();

        when(bookingsMock.containsKey(1L)).thenReturn(true);

        bookingRepository.updateBooking(booking);
        verify(bookingsMock, times(1)).put(1L, booking);
    }

    @Test
    void testIsTimeValid() {
        Booking existingBooking = Booking.builder().id(1L).startTime(LocalDateTime.now().plusDays(1)).endTime(LocalDateTime.now().plusDays(1).plusHours(1)).build();
        when(bookingsMock.values()).thenReturn(List.of(existingBooking));

        LocalDateTime startTime = LocalDateTime.now().plusDays(1).plusMinutes(30);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).plusHours(1).plusMinutes(30);
        assertFalse(bookingRepository.isTimeValid(startTime, endTime));

        startTime = LocalDateTime.now().plusDays(2);
        endTime = LocalDateTime.now().plusDays(2).plusHours(1);
        assertTrue(bookingRepository.isTimeValid(startTime, endTime));
    }

    @Test
    void testIsInPast() {
        assertTrue(BookingRepository.isInPast(LocalDateTime.now().minusDays(1)));
        assertFalse(BookingRepository.isInPast(LocalDateTime.now().plusDays(1)));
    }

    @Test
    void testCreateBookingWithInvalidTime() {
        Booking booking = Booking.builder().id(1L).startTime(LocalDateTime.now().minusDays(1)).endTime(LocalDateTime.now().plusDays(1)).build();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingRepository.createBooking(booking);
        });

        String expectedMessage = "Booking start time cannot be in the past";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
