package se.ticket.relnotes.jira.domain;

/**
 */
public class Project implements Comparable<Project> {

    private String key;

    public Project(String key) {
        this.key = key;
    }

    @Override
    public int compareTo(Project o) {
        return this.key.compareTo(o.key);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Project{" +
                "key='" + key + '\'' +
                '}';
    }
}
