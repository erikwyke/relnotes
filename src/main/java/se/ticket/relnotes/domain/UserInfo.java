package se.ticket.relnotes.domain;

/**
 */
public class UserInfo {
    private String jiraUsername;
    private String jiraPassword;
    private String githubUsername;
    private String githubPassword;

    public String getJiraUsername() {
        return jiraUsername;
    }

    public void setJiraUsername(String jiraUsername) {
        this.jiraUsername = jiraUsername;
    }

    public String getJiraPassword() {
        return jiraPassword;
    }

    public void setJiraPassword(String jiraPassword) {
        this.jiraPassword = jiraPassword;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getGithubPassword() {
        return githubPassword;
    }

    public void setGithubPassword(String githubPassword) {
        this.githubPassword = githubPassword;
    }

    public String getPlainGithubCredentials() {
        return getGithubUsername()+":"+getGithubPassword();
    }

    public String getPlainJiraCredentials() {
        return getJiraUsername()+":"+getJiraPassword();
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "jiraUsername='" + jiraUsername + '\'' +
                ", jiraPassword='" + jiraPassword + '\'' +
                ", githubUsername='" + githubUsername + '\'' +
                ", githubPassword='" + githubPassword + '\'' +
                '}';
    }
}
