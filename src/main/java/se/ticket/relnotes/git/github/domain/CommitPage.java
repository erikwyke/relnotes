package se.ticket.relnotes.git.github.domain;

/**
 */
public class CommitPage {
    private String sha1;
    private Commit commit;

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }
}
