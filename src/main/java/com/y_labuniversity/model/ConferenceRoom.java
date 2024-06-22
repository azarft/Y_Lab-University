package com.y_labuniversity.model;

import lombok.*;

/**
 * Represents a conference room in the system.
 * The conference room has an id, a name, and a capacity.
 * This class uses Lombok annotations for boilerplate code reduction.
 *
 * <p>
 * Annotations used:
 * <ul>
 *     <li>{@link Getter} - Generates getter methods for all fields</li>
 *     <li>{@link Setter} - Generates setter methods for all fields</li>
 *     <li>{@link Builder} - Provides a builder pattern for object creation</li>
 *     <li>{@link AllArgsConstructor} - Generates a constructor with parameters for all fields</li>
 *     <li>{@link NoArgsConstructor} - Generates a no-argument constructor</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * ConferenceRoom conferenceRoom = ConferenceRoom.builder()
 *                 .id(1)
 *                 .name("ConsoleApplication Conference Room")
 *                 .capacity(20)
 *                 .build();
 * }</pre>
 * </p>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoom {
    private Long id;
    private String name;
    private int capacity;
}
