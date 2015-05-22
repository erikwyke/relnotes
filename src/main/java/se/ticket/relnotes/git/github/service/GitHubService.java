package se.ticket.relnotes.git.github.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.ticket.relnotes.domain.ReleaseNotesConfiguration;
import se.ticket.relnotes.git.github.domain.Commit;
import se.ticket.relnotes.git.github.domain.CommitPage;
import se.ticket.relnotes.git.github.domain.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 */
@Service
public class GitHubService {
    @Autowired
    private ReleaseNotesConfiguration releaseNotesConfiguration;

    public List<Repository> getRepositoriesForOrganization(String organization) {
        HttpEntity<String> request = getStringHttpEntity();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Repository[]> response = restTemplate.exchange("https://api.github.com/orgs/"+organization+"/repos", HttpMethod.GET, request, Repository[].class);
        Repository[] body = response.getBody();
        List<Repository> repositories = Arrays.asList(body);
        return repositories;
    }



    public List<Commit> getCommitsForRepositoriesFromDate(List<Repository> repositories) {
        HttpEntity<String> request = getStringHttpEntity();
        RestTemplate restTemplate = new RestTemplate();
        List<Commit> allCommits = new ArrayList();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        for (Repository repository : repositories) {
            ResponseEntity<CommitPage[]> response = restTemplate.exchange(repository.getUrl()+"/commits?since="+df1.format(repository.getFromDate()),HttpMethod.GET, request, CommitPage[].class);
            CommitPage[] body = response.getBody();
            List<CommitPage> commitPages = Arrays.asList(body);
            for (CommitPage commitPage : commitPages) {
                allCommits.add(commitPage.getCommit());
            }
        }
        return allCommits;
    }

    private HttpEntity<String> getStringHttpEntity() {
        byte[] plainCredsBytes = releaseNotesConfiguration.getUserInfo().getPlainGithubCredentials().getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return new HttpEntity<String>(headers);
    }

}
