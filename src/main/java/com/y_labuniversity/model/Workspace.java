package com.y_labuniversity.model;


import lombok.*;

/**
 * Represents a workspace in the system.
 * The workspace has an id, a name, and a capacity.
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
 * Workspace workspace = Workspace.builder()
 *                 .id(1)
 *                 .name("Research Room")
 *                 .capacity(10)
 *                 .build();
 * }</pre>
 * </p>
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Workspace {
    private Long id;
    private String name;
    private int capacity;
}
