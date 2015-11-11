package se.ticket.relnotes.jira.service;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.springframework.stereotype.Component;
import se.ticket.relnotes.git.github.domain.Commit;
import se.ticket.relnotes.jira.domain.JiraIssue;
import se.ticket.relnotes.jira.domain.Project;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
@Component
public class JiraIssueMatcher {
    private final Pattern p;

    public JiraIssueMatcher() {
        p = Pattern.compile("(\\w+)-(\\d+)(.*)", Pattern.DOTALL);
    }

    JiraIssue parse(String commitmessage) {
        Matcher m = p.matcher(commitmessage.trim());
        if (m.matches()) {
            String firstlineOfMessage = m.group(3).split("\n")[0].trim();
            if (firstlineOfMessage.startsWith("-")) {
                firstlineOfMessage = firstlineOfMessage.substring(1);
            }
            firstlineOfMessage = " - " + firstlineOfMessage;
            return new JiraIssue(m.group(1), Integer.parseInt(m.group(2)), firstlineOfMessage);
        }
        return null;
    }

    public Map<Project, Collection<JiraIssue>> projectToIssuesFromCommits(List<Commit> commits) {
        Multimap<Project, JiraIssue> proj = TreeMultimap.create();
        for (Commit commit : commits) {
            JiraIssue jiraIssue = parse(commit.getMessage());
            if (jiraIssue != null) {
                proj.put(jiraIssue.getProject(), jiraIssue);
            }
        }
        return proj.asMap();
    }
}
