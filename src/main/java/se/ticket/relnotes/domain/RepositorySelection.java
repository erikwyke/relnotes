package se.ticket.relnotes.domain;

import se.ticket.relnotes.git.github.domain.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class RepositorySelection {
    private List<Repository> repositories;

    public RepositorySelection() {
        repositories = new ArrayList<>();
    }

    public RepositorySelection(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }
}
