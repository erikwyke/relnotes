package se.ticket.relnotes.git.github.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 */
public class Repository {
    private String name;
    private String url;
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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
