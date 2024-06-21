package com.y_labuniversity.repositories;

import com.y_labuniversity.model.Workspace;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository class for managing workspaces.
 * Provides methods for CRUD operations on workspace data.
 */
public class WorkspaceRepository {

    private Map<Long, Workspace> workspaces = new HashMap<>();

    /**
     * Retrieves all workspaces in the repository.
     *
     * @return a collection of all workspaces
     */
    public Collection<Workspace> getAllWorkspaces() {
        return workspaces.values();
    }

    /**
     * Retrieves a workspace by its ID.
     *
     * @param id the ID of the workspace to retrieve
     * @return the workspace with the specified ID
     * @throws IllegalArgumentException if the workspace does not exist
     */
    public Workspace getWorkspace(Long id) {
        if (!workspaces.containsKey(id)) {
            throw new IllegalArgumentException("Workspace with id " + id + " not exist");
        }
        return workspaces.get(id);
    }

    /**
     * Creates a new workspace in the repository.
     *
     * @param workspace the workspace to create
     * @throws IllegalArgumentException if a workspace with the same ID already exists
     */
    public void createWorkspace(Workspace workspace) {
        if (workspaces.containsKey(workspace.getId())) {
            throw new IllegalArgumentException("Workspace already exists");
        }
        workspaces.put(workspace.getId(), workspace);
    }

    /**
     * Updates an existing workspace in the repository.
     *
     * @param workspace the workspace to update
     * @throws IllegalArgumentException if the workspace does not exist
     */
    public void updateWorkspace(Workspace workspace) {
        if (!workspaces.containsKey(workspace.getId())) {
            throw new IllegalArgumentException("Workspace does not exist");
        }
        workspaces.put(workspace.getId(), workspace);
    }

    /**
     * Deletes a workspace from the repository.
     *
     * @param id the ID of the workspace to delete
     * @throws IllegalArgumentException if the workspace does not exist
     */
    public void deleteWorkspace(Long id) {
        if (!workspaces.containsKey(id)) {
            throw new IllegalArgumentException("Workspace does not exist");
        }
        workspaces.remove(id);
    }
}
