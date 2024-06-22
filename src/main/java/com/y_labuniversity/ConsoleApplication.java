package com.y_labuniversity;

import com.y_labuniversity.bootstrap.InitData;
import com.y_labuniversity.menu.DisplayMenu;
import com.y_labuniversity.menu.MenuOption;
import com.y_labuniversity.model.Role;
import com.y_labuniversity.model.User;
import com.y_labuniversity.services.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

/**
 * Console application for managing coworking service's workspaces, conference rooms, bookings, and users.
 */
@Getter
@Setter
public class ConsoleApplication {
    private User currentUser;
    private Role role;

    private final Scanner scanner = new Scanner(System.in);
    private final DisplayMenu displayMenu = new DisplayMenu();
    private final AdminService adminService = new AdminService();

    private UserService userService = new UserService();
    private BookingService bookingService = new BookingService();
    private WorkspaceService workspaceService = new WorkspaceService();
    private ConferenceRoomService conferenceRoomService = new ConferenceRoomService();

    /**
     * Main method to start the console application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        ConsoleApplication app = new ConsoleApplication();
        app.start();
    }

    /**
     * Starts the console application.
     */
    public void start() {
        boolean exit = false;
        InitData initData = new InitData();
        initData.initData(userService, workspaceService, conferenceRoomService, bookingService);
        while (!exit) {
            displayMenu.displayAuthMenu();
            MenuOption authMenuOption = displayMenu.getAuthMenuChoice();
            if (authMenuOption == MenuOption.REGISTER_USER) {
                currentUser = register();
                setCurrentUser(currentUser);
            } else if (authMenuOption == MenuOption.LOGIN_USER) {
                currentUser = login();
                setCurrentUser(currentUser);
            } else {
                exit = true;
            }
            setRole(currentUser);
            mainMenu();
        }
    }

    /**
     * Displays the main menu and handles user choices.
     */
    public void mainMenu() {
        while (true) {
            displayMenu.displayMainMenu(role);
            MenuOption mainMenuOption = displayMenu.getMainMenuChoice(role);

            switch (mainMenuOption) {
                case LOGOUT:
                    return;
                case WORKSPACES_CONFERENCE_ROOMS:
                    handleWorkspacesAndConferenceRooms();
                    break;
                case BOOKED:
                    handleBooked();
                    break;
                case MANAGE_ACCOUNT:
                    handleManageAccount();
                    break;
                case MANAGE_WORKSPACES_CONFERENCE_ROOMS:
                    handleManageWorkspacesAndConferenceRooms();
                    break;
                case MANAGE_BOOKINGS:
                    handleManageBookings();
                    break;
                case MANAGE_USERS:
                    handleManageUsers();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles displaying workspaces and conference rooms.
     */
    private void handleWorkspacesAndConferenceRooms() {
        bookingService.showWorkspacesAndConferenceRooms(workspaceService.getAllWorkspaces(), conferenceRoomService.getAllConferenceRooms());
    }

    /**
     * Handles displaying booked resources.
     */
    private void handleBooked() {
        bookingService.handleBooked();
    }

    /**
     * Handles managing the current user's account.
     */
    private void handleManageAccount() {
        userService.handleManageAccount();
    }

    /**
     * Handles managing workspaces and conference rooms.
     */
    private void handleManageWorkspacesAndConferenceRooms() {
        adminService.handleManageWorkspacesAndConferenceRooms(workspaceService, conferenceRoomService);
    }

    /**
     * Handles managing bookings.
     */
    private void handleManageBookings() {
        adminService.handleManageBookings(bookingService, workspaceService, conferenceRoomService);
    }

    /**
     * Handles managing users.
     */
    private void handleManageUsers() {
        adminService.handleManageUsers(userService);
    }

    /**
     * Handles user login.
     *
     * @return the logged-in user
     */
    public User login() {
        System.out.println("Login");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            return userService.authenticate(username, password);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return login();
        }
    }

    /**
     * Handles user registration.
     *
     * @return the registered user
     */
    public User register() {
        System.out.println("Registration");
        System.out.print("Full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(password);

        try {
            return userService.register(user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return register();
        }
    }

    /**
     * Sets the role of the current user.
     *
     * @param user the current user
     */
    public void setRole(User user) {
        if ("admin".equals(user.getUsername())) {
            role = Role.ADMIN;
        } else {
            role = Role.USER;
        }
    }

    /**
     * Sets the current user for the application and updates the services with the current user.
     *
     * @param currentUser the current user
     */
    public void setCurrentUser(User currentUser) {
        userService.setCurrentUser(currentUser);
        bookingService.setCurrentUser(currentUser);
    }
}
