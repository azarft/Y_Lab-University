package com.y_labuniversity.repositories;

import com.y_labuniversity.model.ConferenceRoom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository class for managing conference rooms.
 * Provides methods for CRUD operations on conference room data.
 */
public class ConferenceRoomRepository {

    private Map<Long, ConferenceRoom> conferenceRooms = new HashMap<>();

    /**
     * Retrieves all conference rooms in the repository.
     *
     * @return a collection of all conference rooms
     */
    public Collection<ConferenceRoom> getAllConferenceRooms() {
        return conferenceRooms.values();
    }

    /**
     * Retrieves a conference room by its ID.
     *
     * @param id the ID of the conference room to retrieve
     * @return the conference room with the specified ID
     * @throws IllegalArgumentException if the conference room does not exist
     */
    public ConferenceRoom getConferenceRoom(Long id) {
        if (!conferenceRooms.containsKey(id)) {
            throw new IllegalArgumentException("Conference room with id " + id + " not exist");
        }
        return conferenceRooms.get(id);
    }

    /**
     * Creates a new conference room in the repository.
     *
     * @param conferenceRoom the conference room to create
     * @throws IllegalArgumentException if a conference room with the same ID already exists
     */
    public void createConferenceRoom(ConferenceRoom conferenceRoom) {
        if (conferenceRooms.containsKey(conferenceRoom.getId())) {
            throw new IllegalArgumentException("Conference room already exists");
        }
        conferenceRooms.put(conferenceRoom.getId(), conferenceRoom);
    }

    /**
     * Updates an existing conference room in the repository.
     *
     * @param conferenceRoom the conference room to update
     * @throws IllegalArgumentException if the conference room does not exist
     */
    public void updateConferenceRoom(ConferenceRoom conferenceRoom) {
        if (!conferenceRooms.containsKey(conferenceRoom.getId())) {
            throw new IllegalArgumentException("Conference room does not exist");
        }
        conferenceRooms.put(conferenceRoom.getId(), conferenceRoom);
    }

    /**
     * Deletes a conference room from the repository.
     *
     * @param id the ID of the conference room to delete
     * @throws IllegalArgumentException if the conference room does not exist
     */
    public void deleteConferenceRoom(Long id) {
        if (!conferenceRooms.containsKey(id)) {
            throw new IllegalArgumentException("Conference room does not exist");
        }
        conferenceRooms.remove(id);
    }
}
