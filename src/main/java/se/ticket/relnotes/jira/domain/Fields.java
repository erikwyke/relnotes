package se.ticket.relnotes.jira.domain;

/**
 */
public class Fields {
    private Status status;
    private String summary;
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
