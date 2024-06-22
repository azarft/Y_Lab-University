package com.y_labuniversity.services;

import com.y_labuniversity.model.Workspace;
import com.y_labuniversity.repositories.WorkspaceRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

/**
 * Service class for managing workspaces.
 * Provides methods for CRUD operations on workspace data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceService {

    private WorkspaceRepository workspaceRepository = new WorkspaceRepository();

    /**
     * Retrieves all workspaces.
     *
     * @return a list of all workspaces
     */
    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.getAllWorkspaces().stream().toList();
    }

    /**
     * Retrieves a workspace by its ID.
     *
     * @param id the ID of the workspace to retrieve
     * @return the workspace with the specified ID
     * @throws IllegalArgumentException if the workspace does not exist
     */
    public Workspace getWorkspaceById(Long id) {
        return workspaceRepository.getWorkspace(id);
    }

    /**
     * Creates a new workspace.
     *
     * @param workspace the workspace to create
     * @throws IllegalArgumentException if a workspace with the same ID already exists
     */
    public void createWorkspace(Workspace workspace) {
        workspaceRepository.createWorkspace(workspace);
    }

    /**
     * Updates an existing workspace.
     *
     * @param workspace the workspace to update
     * @throws IllegalArgumentException if the workspace does not exist
     */
    public void updateWorkspace(Workspace workspace) {
        workspaceRepository.updateWorkspace(workspace);
    }

    /**
     * Deletes a workspace.
     *
     * @param id the ID of the workspace to delete
     * @throws IllegalArgumentException if the workspace does not exist
     */
    public void deleteWorkspace(Long id) {
        workspaceRepository.deleteWorkspace(id);
    }
}
