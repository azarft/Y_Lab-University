package RepositoryTest;

import com.y_labuniversity.model.ConferenceRoom;
import com.y_labuniversity.repositories.ConferenceRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConferenceRoomRepositoryTest {
    @Mock
    private Map<Long, ConferenceRoom> conferenceRoomsMock;

    @InjectMocks
    private ConferenceRoomRepository conferenceRoomRepository = new ConferenceRoomRepository();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllConferenceRooms() {
        ConferenceRoom conferenceRoom1 = ConferenceRoom.builder().id(1L).name("Conference Room 101").capacity(20).build();
        ConferenceRoom conferenceRoom2 = ConferenceRoom.builder().id(2L).name("Conference Room 102").capacity(15).build();

        when(conferenceRoomsMock.values()).thenReturn(List.of(conferenceRoom1, conferenceRoom2));

        Collection<ConferenceRoom> allConferenceRooms = conferenceRoomRepository.getAllConferenceRooms();
        assertNotNull(allConferenceRooms);
        assertEquals(2, allConferenceRooms.size());
        assertTrue(allConferenceRooms.contains(conferenceRoom1));
        assertTrue(allConferenceRooms.contains(conferenceRoom2));
    }

    @Test
    void testGetConferenceRoomById() {
        ConferenceRoom conferenceRoom = ConferenceRoom.builder().id(1L).name("Conference Room 101").capacity(20).build();

        when(conferenceRoomsMock.containsKey(1L)).thenReturn(true);
        when(conferenceRoomsMock.get(1L)).thenReturn(conferenceRoom);

        ConferenceRoom retrievedConferenceRoom = conferenceRoomRepository.getConferenceRoom(1L);
        assertNotNull(retrievedConferenceRoom);
        assertEquals(1L, retrievedConferenceRoom.getId());
    }

    @Test
    void testCreateConferenceRoom() {
        ConferenceRoom conferenceRoom = ConferenceRoom.builder().id(1L).name("Conference Room 101").capacity(20).build();

        when(conferenceRoomsMock.containsKey(1L)).thenReturn(false);

        conferenceRoomRepository.createConferenceRoom(conferenceRoom);
        verify(conferenceRoomsMock, times(1)).put(1L, conferenceRoom);
    }

    @Test
    void testDeleteConferenceRoom() {
        when(conferenceRoomsMock.containsKey(1L)).thenReturn(true);

        conferenceRoomRepository.deleteConferenceRoom(1L);
        verify(conferenceRoomsMock, times(1)).remove(1L);
    }

    @Test
    void testUpdateConferenceRoom() {
        ConferenceRoom conferenceRoom = ConferenceRoom.builder().id(1L).name("Conference Room 101").capacity(20).build();

        when(conferenceRoomsMock.containsKey(1L)).thenReturn(true);

        conferenceRoomRepository.updateConferenceRoom(conferenceRoom);
        verify(conferenceRoomsMock, times(1)).put(conferenceRoom.getId(), conferenceRoom);
    }
}
