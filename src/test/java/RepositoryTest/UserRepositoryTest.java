package RepositoryTest;

import com.y_labuniversity.model.User;
import com.y_labuniversity.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private Map<String, User> usersMock;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User("Azar Orsted", "azarorsted", "password123");
        User user2 = new User("Orsted Azar", "orstedazar", "password123");

        when(usersMock.values()).thenReturn(List.of(user1, user2));

        Collection<User> allUsers = userRepository.getAllUsers();
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    void testGetUserByUsername() {
        User user = new User("Azar Orsted", "azarorsted", "password123");

        when(usersMock.containsKey("azarorsted")).thenReturn(true);
        when(usersMock.get("azarorsted")).thenReturn(user);

        User retrievedUser = userRepository.getUserByUsername("azarorsted");
        assertNotNull(retrievedUser);
        assertEquals("azarorsted", retrievedUser.getUsername());
    }

    @Test
    void testCreateUser() {
        User user = new User("Azar Orsted", "azarorsted", "password123");

        when(usersMock.containsKey("azarorsted")).thenReturn(false);

        userRepository.createUser(user);
        verify(usersMock, times(1)).put("azarorsted", user);
    }

    @Test
    void testRemoveUser() {
        when(usersMock.containsKey("azarorsted")).thenReturn(true);

        userRepository.deleteUser("azarorsted");
        verify(usersMock, times(1)).remove("azarorsted");
    }

    @Test
    void testUpdateUser() {
        User user = new User("Azar Orsted", "azarorsted", "newpassword123");

        when(usersMock.containsKey("azarorsted")).thenReturn(true);

        userRepository.updateUser(user.getUsername(), user);
        verify(usersMock, times(1)).put("azarorsted", user);
    }
}

