package ServiceTest;

import com.y_labuniversity.model.Workspace;
import com.y_labuniversity.repositories.WorkspaceRepository;
import com.y_labuniversity.services.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @InjectMocks
    private WorkspaceService workspaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllWorkspaces() {
        Workspace workspace1 = new Workspace(1L, "Workspace 1", 10);
        Workspace workspace2 = new Workspace(2L, "Workspace 2", 20);

        when(workspaceRepository.getAllWorkspaces()).thenReturn(Arrays.asList(workspace1, workspace2));

        List<Workspace> workspaces = workspaceService.getAllWorkspaces();

        assertEquals(2, workspaces.size());
        assertEquals("Workspace 1", workspaces.get(0).getName());
        assertEquals("Workspace 2", workspaces.get(1).getName());
    }

    @Test
    void testGetWorkspaceById() {
        Workspace workspace = new Workspace(1L, "Workspace 1", 10);

        when(workspaceRepository.getWorkspace(1L)).thenReturn(workspace);

        Workspace result = workspaceService.getWorkspaceById(1L);

        assertNotNull(result);
        assertEquals("Workspace 1", result.getName());
    }

    @Test
    void testCreateWorkspace() {
        Workspace workspace = new Workspace(1L, "Workspace 1", 10);

        workspaceService.createWorkspace(workspace);

        ArgumentCaptor<Workspace> workspaceCaptor = ArgumentCaptor.forClass(Workspace.class);
        verify(workspaceRepository, times(1)).createWorkspace(workspaceCaptor.capture());

        Workspace capturedWorkspace = workspaceCaptor.getValue();
        assertEquals("Workspace 1", capturedWorkspace.getName());
    }

    @Test
    void testUpdateWorkspace() {
        Workspace workspace = new Workspace(1L, "Workspace 1", 10);

        workspaceService.updateWorkspace(workspace);

        ArgumentCaptor<Workspace> workspaceCaptor = ArgumentCaptor.forClass(Workspace.class);
        verify(workspaceRepository, times(1)).updateWorkspace(workspaceCaptor.capture());

        Workspace capturedWorkspace = workspaceCaptor.getValue();
        assertEquals("Workspace 1", capturedWorkspace.getName());
    }

    @Test
    void testDeleteWorkspace() {
        workspaceService.deleteWorkspace(1L);

        verify(workspaceRepository, times(1)).deleteWorkspace(1L);
    }

    @Test
    void testCreateWorkspaceThrowsException() {
        Workspace workspace = new Workspace(1L, "Workspace 1", 10);

        doThrow(new IllegalArgumentException("Workspace already exists")).when(workspaceRepository).createWorkspace(workspace);

        assertThrows(IllegalArgumentException.class, () -> workspaceService.createWorkspace(workspace));
    }

    @Test
    void testUpdateWorkspaceThrowsException() {
        Workspace workspace = new Workspace(1L, "Workspace 1", 10);

        doThrow(new IllegalArgumentException("Workspace does not exist")).when(workspaceRepository).updateWorkspace(workspace);

        assertThrows(IllegalArgumentException.class, () -> workspaceService.updateWorkspace(workspace));
    }

    @Test
    void testDeleteWorkspaceThrowsException() {
        doThrow(new IllegalArgumentException("Workspace does not exist")).when(workspaceRepository).deleteWorkspace(1L);

        assertThrows(IllegalArgumentException.class, () -> workspaceService.deleteWorkspace(1L));
    }
}
