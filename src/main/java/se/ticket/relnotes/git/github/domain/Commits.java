package se.ticket.relnotes.git.github.domain;

import java.util.List;

/**
 */
public class Commits {
    private List<CommitPage> commits;

    public List<CommitPage> getCommits() {
        return commits;
    }

    public void setCommits(List<CommitPage> commits) {
        this.commits = commits;
    }
}
