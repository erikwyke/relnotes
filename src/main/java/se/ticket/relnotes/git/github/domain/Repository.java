package se.ticket.relnotes.git.github.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 */
public class Repository {
    private String name;
    private String url;
    @JsonProperty("private")
    private Boolean privateRepo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    private boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean isPrivateRepo() {
        return privateRepo;
    }

    public void setPrivateRepo(boolean privateRepo) {
        this.privateRepo = privateRepo;
    }
}
