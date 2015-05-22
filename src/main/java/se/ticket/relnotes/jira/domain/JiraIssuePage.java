package se.ticket.relnotes.jira.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssuePage {
    private Integer id;
    private Fields fields;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }
}
