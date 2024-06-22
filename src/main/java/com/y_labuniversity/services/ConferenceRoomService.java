package com.y_labuniversity.services;

import com.y_labuniversity.model.ConferenceRoom;
import com.y_labuniversity.repositories.ConferenceRoomRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

/**
 * Service class for managing conference rooms.
 * Provides methods for CRUD operations on conference room data.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomService {

    private ConferenceRoomRepository conferenceRoomRepository = new ConferenceRoomRepository();

    /**
     * Retrieves all conference rooms.
     *
     * @return a list of all conference rooms
     */
    public List<ConferenceRoom> getAllConferenceRooms() {
        return conferenceRoomRepository.getAllConferenceRooms().stream().toList();
    }

    /**
     * Retrieves a conference room by its ID.
     *
     * @param id the ID of the conference room to retrieve
     * @return the conference room with the specified ID
     * @throws IllegalArgumentException if the conference room does not exist
     */
    public ConferenceRoom getConferenceRoomById(Long id) {
        return conferenceRoomRepository.getConferenceRoom(id);
    }

    /**
     * Creates a new conference room.
     *
     * @param conferenceRoom the conference room to create
     * @throws IllegalArgumentException if a conference room with the same ID already exists
     */
    public void createConferenceRoom(ConferenceRoom conferenceRoom) {
        conferenceRoomRepository.createConferenceRoom(conferenceRoom);
    }

    /**
     * Updates an existing conference room.
     *
     * @param conferenceRoom the conference room to update
     * @throws IllegalArgumentException if the conference room does not exist
     */
    public void updateConferenceRoom(ConferenceRoom conferenceRoom) {
        conferenceRoomRepository.updateConferenceRoom(conferenceRoom);
    }

    /**
     * Deletes a conference room.
     *
     * @param id the ID of the conference room to delete
     * @throws IllegalArgumentException if the conference room does not exist
     */
    public void deleteConferenceRoom(Long id) {
        conferenceRoomRepository.deleteConferenceRoom(id);
    }
}
