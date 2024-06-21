package com.y_labuniversity.model;

import lombok.*;

/**
 * Represents a user in the system.
 * The user has a full name, a username, and a password.
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
 * User user = User.builder()
 *                 .fullName("Azanov Argen")
 *                 .userName("azar")
 *                 .password("azarft01")
 *                 .build();
 * }</pre>
 * </p>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String fullName;
    private String username;
    private String password;
}
