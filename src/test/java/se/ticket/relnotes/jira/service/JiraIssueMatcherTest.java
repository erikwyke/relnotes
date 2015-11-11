package se.ticket.relnotes.jira.service;

import org.junit.Test;
import se.ticket.relnotes.jira.domain.JiraIssue;

import static org.junit.Assert.*;

/**
 * @author Erik Wyke
 */
public class JiraIssueMatcherTest {

    @Test
    public void testUnableToParseSomeIssueKeys() throws Exception {
        JiraIssueMatcher jim = new JiraIssueMatcher();
        JiraIssue parse = jim.parse(" BKK-788 Ablababbla");
        assertEquals("BKK-788", parse.getKey());
        assertEquals(" - Ablababbla", parse.getSummary());
    }
}