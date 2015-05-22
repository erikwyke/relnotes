package se.ticket.relnotes.jira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.ticket.relnotes.domain.ReleaseNotesConfiguration;
import se.ticket.relnotes.jira.domain.JiraIssue;
import se.ticket.relnotes.jira.domain.JiraIssuePage;

import java.util.Base64;
/**
 */
@Component
public class JiraIssueInformationFetcher {
    @Autowired
    private ReleaseNotesConfiguration releaseNotesConfiguration;


    public void addInformation(JiraIssue jiraIssue) {
        byte[] plainCredsBytes = releaseNotesConfiguration.getUserInfo().getPlainJiraCredentials().getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<JiraIssuePage> response = restTemplate.exchange("https://zlatan.atlassian.net/rest/api/latest/issue/" + jiraIssue.getKey() +"?fields=status,summary", HttpMethod.GET, request, JiraIssuePage.class);
        JiraIssuePage jip = response.getBody();

        jiraIssue.setStatus(jip.getFields().getStatus().getName());
        jiraIssue.setSummary(jip.getFields().getSummary());
    }
}
