package com.y_labuniversity.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 *
 * Represents a booking in the system.
 * The booking has an id, a user, a resource which can be workspace or conference room, a start time, an end time.
 * This class uses Lombok annotations for boilerplate code reduction.
 *
 * <p>
 * Annotations used
 * <ul>
 *     <li>{@link Getter} - Generates getters for all fields</li>
 *     <li>{@link Setter} - Generates setters for all fields</li>
 *     <li>{@link Builder} - Provide a builder patter for the object</li>
 *     <li>{@link AllArgsConstructor} - Generates a constructor with parameters for all fields</li>
 *     <li>{@link NoArgsConstructor} - Generates a no-argument constructor</li>
 *
 * </ul>
 * </p>
 *
 * <p>
 *   Example usage:
 *   <pre>{@code
 *   Booking booking = Booking.builder()
 *                   .id(1)
 *                   .user(user)
 *                   .resource(resource)
 *                   .startTime(LocalDateTime.of(2024, 6, 20, 10, 0))
 *                   .endTime(LocalDateTime.of(2024, 6, 20, 12, 0))
 *                   .build();
 *   }</pre>
 *   </p>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private Long id;
    private User user;
    private Object resource; // can be Workspace or ConferenceRoom
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
