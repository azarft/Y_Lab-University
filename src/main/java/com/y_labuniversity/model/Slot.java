package com.y_labuniversity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Represents a time slot for booking a resource.
 * Each slot has a start time and an end time.
 *
 * <p>
 * This class uses Lombok annotations for boilerplate code reduction:
 * <ul>
 *     <li>{@link Getter} - Generates getter methods for all fields</li>
 *     <li>{@link Setter} - Generates setter methods for all fields</li>
 *     <li>{@link AllArgsConstructor} - Generates a constructor with parameters for all fields</li>
 *     <li>{@link NoArgsConstructor} - Generates a no-argument constructor</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0);
 * LocalDateTime end = LocalDateTime.of(2024, 6, 1, 12, 0);
 * Slot slot = new Slot(start, end);
 * }</pre>
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Slot {
    /**
     * The start time of the slot.
     */
    private LocalDateTime startTime;

    /**
     * The end time of the slot.
     */
    private LocalDateTime endTime;
}
