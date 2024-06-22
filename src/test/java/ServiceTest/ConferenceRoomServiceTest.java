package ServiceTest;

import com.y_labuniversity.model.ConferenceRoom;
import com.y_labuniversity.repositories.ConferenceRoomRepository;
import com.y_labuniversity.services.ConferenceRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConferenceRoomServiceTest {

    @Mock
    private ConferenceRoomRepository conferenceRoomRepository;

    @InjectMocks
    private ConferenceRoomService conferenceRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllConferenceRooms() {
        ConferenceRoom room1 = new ConferenceRoom(1L, "Conference Room 1", 10);
        ConferenceRoom room2 = new ConferenceRoom(2L, "Conference Room 2", 20);

        when(conferenceRoomRepository.getAllConferenceRooms()).thenReturn(Arrays.asList(room1, room2));

        List<ConferenceRoom> conferenceRooms = conferenceRoomService.getAllConferenceRooms();

        assertEquals(2, conferenceRooms.size());
        assertEquals("Conference Room 1", conferenceRooms.get(0).getName());
        assertEquals("Conference Room 2", conferenceRooms.get(1).getName());
    }

    @Test
    void testGetConferenceRoomById() {
        ConferenceRoom room = new ConferenceRoom(1L, "Conference Room 1", 10);

        when(conferenceRoomRepository.getConferenceRoom(1L)).thenReturn(room);

        ConferenceRoom result = conferenceRoomService.getConferenceRoomById(1L);

        assertNotNull(result);
        assertEquals("Conference Room 1", result.getName());
    }

    @Test
    void testCreateConferenceRoom() {
        ConferenceRoom room = new ConferenceRoom(1L, "Conference Room 1", 10);

        conferenceRoomService.createConferenceRoom(room);

        ArgumentCaptor<ConferenceRoom> roomCaptor = ArgumentCaptor.forClass(ConferenceRoom.class);
        verify(conferenceRoomRepository, times(1)).createConferenceRoom(roomCaptor.capture());

        ConferenceRoom capturedRoom = roomCaptor.getValue();
        assertEquals("Conference Room 1", capturedRoom.getName());
    }

    @Test
    void testUpdateConferenceRoom() {
        ConferenceRoom room = new ConferenceRoom(1L, "Conference Room 1", 10);

        conferenceRoomService.updateConferenceRoom(room);

        ArgumentCaptor<ConferenceRoom> roomCaptor = ArgumentCaptor.forClass(ConferenceRoom.class);
        verify(conferenceRoomRepository, times(1)).updateConferenceRoom(roomCaptor.capture());

        ConferenceRoom capturedRoom = roomCaptor.getValue();
        assertEquals("Conference Room 1", capturedRoom.getName());
    }

    @Test
    void testDeleteConferenceRoom() {
        conferenceRoomService.deleteConferenceRoom(1L);

        verify(conferenceRoomRepository, times(1)).deleteConferenceRoom(1L);
    }

    @Test
    void testCreateConferenceRoomThrowsException() {
        ConferenceRoom room = new ConferenceRoom(1L, "Conference Room 1", 10);

        doThrow(new IllegalArgumentException("Conference room already exists")).when(conferenceRoomRepository).createConferenceRoom(room);

        assertThrows(IllegalArgumentException.class, () -> conferenceRoomService.createConferenceRoom(room));
    }

    @Test
    void testUpdateConferenceRoomThrowsException() {
        ConferenceRoom room = new ConferenceRoom(1L, "Conference Room 1", 10);

        doThrow(new IllegalArgumentException("Conference room does not exist")).when(conferenceRoomRepository).updateConferenceRoom(room);

        assertThrows(IllegalArgumentException.class, () -> conferenceRoomService.updateConferenceRoom(room));
    }

    @Test
    void testDeleteConferenceRoomThrowsException() {
        doThrow(new IllegalArgumentException("Conference room does not exist")).when(conferenceRoomRepository).deleteConferenceRoom(1L);

        assertThrows(IllegalArgumentException.class, () -> conferenceRoomService.deleteConferenceRoom(1L));
    }
}
