package RepositoryTest;

import com.y_labuniversity.model.Workspace;
import com.y_labuniversity.repositories.WorkspaceRepository;
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

public class WorkspaceRepositoryTest {
    @Mock
    private Map<Long, Workspace> workspacesMock;

    @InjectMocks
    private WorkspaceRepository workspaceRepository = new WorkspaceRepository();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllWorkspaces() {
        Workspace workspace1 = Workspace.builder().id(1L).name("Workspace 101").capacity(10).build();
        Workspace workspace2 = Workspace.builder().id(2L).name("Workspace 102").capacity(8).build();

        when(workspacesMock.values()).thenReturn(List.of(workspace1, workspace2));

        Collection<Workspace> allWorkspaces = workspaceRepository.getAllWorkspaces();
        assertNotNull(allWorkspaces);
        assertEquals(2, allWorkspaces.size());
        assertTrue(allWorkspaces.contains(workspace1));
        assertTrue(allWorkspaces.contains(workspace2));
    }

    @Test
    void testGetWorkspaceById() {
        Workspace workspace = Workspace.builder().id(1L).name("Workspace 101").capacity(10).build();

        when(workspacesMock.containsKey(1L)).thenReturn(true);
        when(workspacesMock.get(1L)).thenReturn(workspace);

        Workspace retrievedWorkspace = workspaceRepository.getWorkspace(1L);
        assertNotNull(retrievedWorkspace);
        assertEquals(1L, retrievedWorkspace.getId());
    }

    @Test
    void testCreateWorkspace() {
        Workspace workspace = Workspace.builder().id(1L).name("Workspace 101").capacity(10).build();

        when(workspacesMock.containsKey(1L)).thenReturn(false);

        workspaceRepository.createWorkspace(workspace);
        verify(workspacesMock, times(1)).put(1L, workspace);
    }

    @Test
    void testDeleteWorkspace() {
        when(workspacesMock.containsKey(1L)).thenReturn(true);

        workspaceRepository.deleteWorkspace(1L);
        verify(workspacesMock, times(1)).remove(1L);
    }

    @Test
    void testUpdateUser() {
        Workspace workspace = Workspace.builder().id(1L).name("Workspace 101").capacity(10).build();

        when(workspacesMock.containsKey(1L)).thenReturn(true);

        workspaceRepository.updateWorkspace(workspace);
        verify(workspacesMock, times(1)).put(workspace.getId(), workspace);
    }
}

