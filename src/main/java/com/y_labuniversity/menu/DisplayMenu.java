package com.y_labuniversity.menu;

import com.y_labuniversity.model.Role;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class manages the display and input handling for menus in the Y Lab University coworking service application.
 */
public class DisplayMenu {
    private final Scanner scanner;

    /**
     * Constructs a DisplayMenu object with a Scanner initialized for user input.
     */
    public DisplayMenu() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu based on the user's role.
     *
     * @param role The role of the user (ADMIN or USER).
     */
    public void displayMainMenu(Role role) {
        if (role == Role.ADMIN) {
            displayMainMenuForAdmin();
        } else {
            displayMainMenuForUser();
        }
    }

    /**
     * Retrieves the main menu choice based on the user's role.
     *
     * @param role The role of the user (ADMIN or USER).
     * @return The chosen MenuOption based on the user input.
     */
    public MenuOption getMainMenuChoice(Role role) {
        if (role == Role.ADMIN) {
            return getMainMenuAdminChoice();
        } else {
            return getMainMenuUserChoice();
        }
    }

    /**
     * Retrieves the main menu choice for a regular user.
     *
     * @return The chosen MenuOption for a regular user based on the input.
     */
    public MenuOption getMainMenuUserChoice() {
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                return MenuOption.WORKSPACES_CONFERENCE_ROOMS;
            case 2:
                return MenuOption.BOOKED;
            case 3:
                return MenuOption.MANAGE_ACCOUNT;
            case 4:
                return MenuOption.LOGOUT;
            default:
                System.out.println("Invalid choice. Please try again.");
                return getMainMenuUserChoice();
        }
    }

    /**
     * Displays the main menu options for the administrator.
     */
    public void displayMainMenuForAdmin() {
        System.out.println("Welcome to Y Lab University Coworking Service dear Admin");
        System.out.println("1. Manage Workspaces/Conference rooms");
        System.out.println("2. Manage Bookings");
        System.out.println("3. Manage Users");
        System.out.println("4. Logout");
    }

    /**
     * Displays the main menu options for a regular user.
     */
    public void displayMainMenuForUser() {
        System.out.println("Welcome to Y Lab University Coworking Service dear User");
        System.out.println("1. Workspaces and Conference rooms");
        System.out.println("2. Booked Workspaces/Conference rooms");
        System.out.println("3. Manage my account");
        System.out.println("4. Logout");
    }

    /**
     * Retrieves the main menu choice for an administrator.
     *
     * @return The chosen MenuOption for an administrator based on the input.
     */
    public MenuOption getMainMenuAdminChoice() {
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                return MenuOption.MANAGE_WORKSPACES_CONFERENCE_ROOMS;
            case 2:
                return MenuOption.MANAGE_BOOKINGS;
            case 3:
                return MenuOption.MANAGE_USERS;
            case 4:
                return MenuOption.LOGOUT;
            default:
                System.out.println("Invalid choice. Please try again.");
                return getMainMenuAdminChoice();
        }
    }

    /**
     * Displays the authentication menu options.
     */
    public void displayAuthMenu() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    /**
     * Retrieves the authentication menu choice.
     *
     * @return The chosen MenuOption for authentication based on the input.
     */
    public MenuOption getAuthMenuChoice() {
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                return MenuOption.REGISTER_USER;
            case 2:
                return MenuOption.LOGIN_USER;
            case 3:
                return MenuOption.LOGOUT;
            default:
                System.out.println("Invalid choice. Please try again.");
                return getAuthMenuChoice();
        }
    }

    /**
     * Helper method to get integer input from the user.
     *
     * @param prompt The prompt message to display to the user.
     * @return The integer input provided by the user.
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume invalid input
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }
}
