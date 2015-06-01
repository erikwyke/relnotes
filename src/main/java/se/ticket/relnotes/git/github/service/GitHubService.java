package se.ticket.relnotes.git.github.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
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

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 */
@Service
public class GitHubService {
    @Autowired
    private ReleaseNotesConfiguration releaseNotesConfiguration;

    public List<Repository> getRepositoriesForOrganization(String organization) throws UnsupportedEncodingException {
        HttpEntity<String> request = getStringHttpEntity();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Repository[]> response = restTemplate.exchange("https://api.github.com/orgs/" + organization + "/repos", HttpMethod.GET, request, Repository[].class);
        Repository[] body = response.getBody();
        List<Repository> repositories = Arrays.asList(body);
        // Sort by privaterepos first
        Collections.sort(repositories, new Comparator<Repository>() {
            @Override
            public int compare(Repository o1, Repository o2) {
                return o2.isPrivateRepo().compareTo(o1.isPrivateRepo());
            }
        });
        return repositories;
    }


    public List<Commit> getCommitsForRepositoriesFromDate(List<Repository> repositories) throws UnsupportedEncodingException {
        HttpEntity<String> request = getStringHttpEntity();
        RestTemplate restTemplate = new RestTemplate();
        List<Commit> allCommits = new ArrayList();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        for (Repository repository : repositories) {
            ResponseEntity<CommitPage[]> response = restTemplate.exchange(repository.getUrl() + "/commits?since=" + df1.format(repository.getFromDate()), HttpMethod.GET, request, CommitPage[].class);
            String nextUrl = getNextURL(response);
            while (nextUrl != null) {
                response = restTemplate.exchange(nextUrl, HttpMethod.GET, request, CommitPage[].class);
                nextUrl = getNextURL(response);
                CommitPage[] body = response.getBody();
                List<CommitPage> commitPages = Arrays.asList(body);
                for (CommitPage commitPage : commitPages) {
                    allCommits.add(commitPage.getCommit());
                }
            }
        }
        return allCommits;
    }

    private String getNextURL(ResponseEntity<CommitPage[]> response) {
        HttpHeaders headers = response.getHeaders();
        List<String> link = headers.get("Link");
        Map<String, String> map = new HashMap<>();
        if (link != null) {
            String linkHeader = link.get(0);
            String[] allLinks = linkHeader.split(",");
            for (String s : allLinks) {
                String[] urlAndRel = s.split(";");
                String url = urlAndRel[0];
                String rel = urlAndRel[1];
                String replacedAndTrimmedUrl = url.replace("<", "").replace(">", "").trim();
                String[] split = rel.split("=");
                String fixedRel = split[1].replace("\"", "").trim();
                map.put(fixedRel, replacedAndTrimmedUrl);
            }
            return map.get("next");
        }
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private HttpEntity<String> getStringHttpEntity() throws UnsupportedEncodingException {
        byte[] plainCredsBytes = releaseNotesConfiguration.getUserInfo().getPlainGithubCredentials().getBytes();
        String base64Creds = DatatypeConverter.printBase64Binary(plainCredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return new HttpEntity<String>(headers);
    }

}
