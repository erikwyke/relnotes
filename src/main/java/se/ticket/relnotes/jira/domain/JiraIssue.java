package se.ticket.relnotes.jira.domain;

import se.ticket.relnotes.git.github.domain.Commit;
import se.ticket.relnotes.git.github.domain.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 */
public class JiraIssue implements Comparable<JiraIssue> {
    private final Project project;
    private final Integer number;
    private String status;
    private String summary;
    private Set<Repository> repositories;
    private Set<Commit> commits;

    public JiraIssue(String key, Integer number, String summary) {
        this.project = new Project(key);
        this.number = number;
        this.summary=summary;
        this.repositories = new HashSet<>();
        this.commits = new HashSet<>();
    }

    public Project getProject() {
        return project;
    }

    public Integer getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return project.getKey() + "-" + number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void addRepository(Repository repository) {
        repositories.add(repository);
    }

    public void addCommit(Commit commit) {
        commits.add(commit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiraIssue jiraIssue = (JiraIssue) o;

        if (number != jiraIssue.number) return false;
        if (project != null ? !project.equals(jiraIssue.project) : jiraIssue.project != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = project != null ? project.hashCode() : 0;
        result = 31 * result + number;
        return result;
    }

    @Override
    public String toString() {
        return "JiraIssue{" +
                "project='" + project.toString() + '\'' +
                ", number=" + number +
                ", status='" + status + '\'' +
                ", description='" + summary + '\'' +
                '}';
    }

    @Override
    public int compareTo(JiraIssue o) {
        return this.getNumber().compareTo(o.getNumber());
    }
}
