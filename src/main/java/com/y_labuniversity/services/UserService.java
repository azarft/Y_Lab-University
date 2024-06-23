package com.y_labuniversity.services;

import com.y_labuniversity.model.User;
import com.y_labuniversity.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Service class for managing user operations such as registration, authentication,
 * account management, and user CRUD operations.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserService {
    private User currentUser;
    private final Scanner scanner = new Scanner(System.in);
    private UserRepository userRepository = new UserRepository();

    /**
     * Registers a new user.
     *
     * @param user The user object containing registration details
     * @return The registered user
     * @throws IllegalArgumentException if a user with the same username already exists
     */
    public User register(User user) {
        if (userRepository.getUserByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        userRepository.createUser(user);
        return user;
    }

    /**
     * Authenticates a user with the provided username and password.
     *
     * @param username The username of the user to authenticate
     * @param password The password of the user
     * @return The authenticated user
     * @throws IllegalArgumentException if invalid credentials are provided
     */
    public User authenticate(String username, String password) {
        try {
            User user = userRepository.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            } else {
                throw new IllegalArgumentException("Invalid credentials");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid credentials", e);
        }
    }

    /**
     * Creates a new user in the repository.
     *
     * @param user The user object to create
     */
    public void createUser(User user) {
        userRepository.createUser(user);
    }

    /**
     * Deletes a user from the repository by username.
     *
     * @param username The username of the user to delete
     */
    public void deleteUser(String username) {
        userRepository.deleteUser(username);
    }

    /**
     * Updates an existing user in the repository.
     *
     * @param username The username of the user to update
     * @param user     The updated user object
     */
    public void updateUser(String username, User user) {
        userRepository.updateUser(username, user);
    }

    /**
     * Handles the management of the current user's account, allowing them to change
     * full name, username, password, or delete their account.
     */
    public void handleManageAccount() {
        while (true) {
            System.out.println("Full name: " + currentUser.getFullName());
            System.out.println("Username: " + currentUser.getUsername());
            System.out.println("----- Manage Account -----");
            System.out.println("1. Change Full Name");
            System.out.println("2. Change Username");
            System.out.println("3. Change Password");
            System.out.println("4. Delete account and exit");
            System.out.println("5. Back");

            int choice = getIntInput("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    changeFullName();
                    break;
                case 2:
                    changeUsername();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    deleteAccount();
                    return; // Exit after account deletion
                case 5:
                    return; // Go back
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    /**
     * Helper method to change the full name of the current user.
     */
    private void changeFullName() {
        System.out.print("Enter new full name: ");
        String newFullName = scanner.nextLine();
        currentUser.setFullName(newFullName);
        userRepository.updateUser(currentUser.getUsername(), currentUser);
        System.out.println("Full name updated successfully.");
    }

    /**
     * Helper method to change the username of the current user.
     */
    private void changeUsername() {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();

        // Check if the new username already exists
        if (userRepository.getUserByUsername(newUsername) != null) {
            System.out.println("Username already exists. Please choose another username.");
            return;
        }

        // Create a copy of currentUser with the updated username
        User updatedUser = User.builder()
                .fullName(currentUser.getFullName())
                .username(newUsername)
                .password(currentUser.getPassword())
                .build();

        // Update the user in the repository
        userRepository.updateUser(currentUser.getUsername(), updatedUser);

        // Update the currentUser reference to the updated user
        currentUser = updatedUser;

        System.out.println("Username updated successfully.");
    }

    /**
     * Helper method to change the password of the current user.
     */
    private void changePassword() {
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();

        if (!currentUser.getPassword().equals(currentPassword)) {
            System.out.println("Incorrect current password.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        userRepository.updateUser(currentUser.getUsername(), currentUser);
        System.out.println("Password updated successfully.");
    }

    /**
     * Helper method to delete the account of the current user.
     */
    private void deleteAccount() {
        System.out.print("Are you sure you want to delete your account? (yes/no): ");
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            userRepository.deleteUser(currentUser.getUsername());
            System.out.println("Account deleted successfully. Goodbye!");
            System.exit(0); // Exit the application after account deletion
        } else {
            System.out.println("Account deletion canceled.");
        }
    }

    /**
     * Helper method to get an integer input from the user within a specified range.
     *
     * @param prompt The prompt message to display
     * @param min    The minimum allowed integer value (inclusive)
     * @param max    The maximum allowed integer value (inclusive)
     * @return The integer entered by the user
     */
    private int getIntInput(String prompt, int min, int max) {
        System.out.print(prompt);
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (input < min || input > max) {
                    System.out.print("Invalid input. Please enter a number between " + min + " and " + max + ": ");
                } else {
                    return input;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume invalid input
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }
}
