package se.ticket.relnotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.ticket.relnotes.domain.ReleaseNotesConfiguration;
import se.ticket.relnotes.domain.RepositorySelection;
import se.ticket.relnotes.git.github.domain.Commit;
import se.ticket.relnotes.git.github.service.GitHubService;
import se.ticket.relnotes.git.github.domain.Repository;
import se.ticket.relnotes.jira.domain.JiraIssue;
import se.ticket.relnotes.jira.service.JiraIssueInformationFetcher;
import se.ticket.relnotes.jira.service.JiraIssueMatcher;
import se.ticket.relnotes.jira.domain.Project;

import java.util.*;

@Controller
public class ReleaseNotesController {
    @Autowired
    private ReleaseNotesConfiguration releaseNotesConfiguration;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private JiraIssueMatcher jiraIssueMatcher;

    @Autowired
    private JiraIssueInformationFetcher jiraIssueInformationFetcher;

    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("repositoryConfiguration", releaseNotesConfiguration);
        return "index";
    }

    @RequestMapping("/selectrepos")
    public String selectrepos(final RepositorySelection repositorySelection, Model model) {
        List<Repository> selectedRepositories = new ArrayList<>();
        for (Repository repository : repositorySelection.getRepositories()) {
           if(repository.isSelected()) {
               selectedRepositories.add(repository);
           }
        }
        List<Commit> commits = gitHubService.getCommitsForRepositoriesFromDate(selectedRepositories);
        Map<Project, Collection<JiraIssue>> jiraIssues = jiraIssueMatcher.projectToIssuesFromCommits(commits);

        model.addAttribute("projectToJiraIssues", jiraIssues);
        return "shownotes";
    }


    @RequestMapping(value = "/listrepos", method = RequestMethod.POST)
    public String listrepos(final ReleaseNotesConfiguration releaseNotesConfiguration,
                            Model model) {
        this.releaseNotesConfiguration.setUserInfo(releaseNotesConfiguration.getUserInfo());
        String organization = releaseNotesConfiguration.getOrganization();
        List<Repository> repositories = gitHubService.getRepositoriesForOrganization(organization);
        model.addAttribute("repositories", repositories);
        model.addAttribute("repositorySelection", new RepositorySelection(repositories));
        return "repositories";
    }

}
