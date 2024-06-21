package com.y_labuniversity.repositories;

import com.y_labuniversity.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository class for managing users.
 * Provides methods for CRUD operations on user data.
 */
public class UserRepository {

    private Map<String, User> users = new HashMap<>();

    /**
     * Retrieves all users in the repository.
     *
     * @return a collection of all users
     */
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the user with the specified username
     * @throws IllegalArgumentException if the user is not found
     */
    public User getUserByUsername(String username) {
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("User " + username + " not found");
        }
        return users.get(username);
    }

    /**
     * Creates a new user in the repository.
     *
     * @param user the user to create
     * @throws IllegalArgumentException if a user with the same username already exists
     */
    public void createUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }
        users.put(user.getUsername(), user);
    }

    /**
     * Removes a user from the repository.
     *
     * @param username the username of the user to remove
     * @throws IllegalArgumentException if the user is not found
     */
    public void deleteUser(String username) {
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("User not found");
        }
        users.remove(username);
    }

    /**
     * Updates an existing user in the repository.
     *
     * @param username the username of the user to update
     * @param user the updated user object
     * @throws IllegalArgumentException if the user is not found
     */
    public void updateUser(String username, User user) {
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("User not found");
        }
        users.put(user.getUsername(), user);
    }
}
