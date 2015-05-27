package dpt.entities.issues;

import com.google.gson.annotations.SerializedName;
import dpt.entities.users.Members;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 */

@Entity
@Table(name = "MILESTONES")
public class Milestones implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SerializedName("_id")
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MILESTONES_ID_SEQ")
    @SequenceGenerator(schema = "PDT", name = "MILESTONES_ID_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "URL")
    private String url;

    @Column(name = "labels_url")
    private String labels_url;

    @SerializedName("number")
    @Column(name = "NUMBERR")
    private BigDecimal number;

    @Column(name = "STATE")
    private String state;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "OPEN_ISSUES")
    private BigDecimal open_issues;

    @Column(name = "CLOSED_ISSUES")
    private BigDecimal closed_issues;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    @Column(name = "DUE_ON")
    @Temporal(TemporalType.DATE)
    private Date due_on;

    @ManyToOne
    @JoinColumn(name = "creator")
    private Members creator;

    @Column(name = "parser", length = 5)
    private String parser;

    @ManyToOne
    @JoinColumn(name = "Repository_id")
    private Repository repository;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getLabels_url() {
        return labels_url;
    }

    public void setLabels_url(String labels_url) {
        this.labels_url = labels_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOpen_issues() {
        return open_issues;
    }

    public void setOpen_issues(BigDecimal open_issues) {
        this.open_issues = open_issues;
    }

    public BigDecimal getClosed_issues() {
        return closed_issues;
    }

    public void setClosed_issues(BigDecimal closed_issues) {
        this.closed_issues = closed_issues;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getDue_on() {
        return due_on;
    }

    public void setDue_on(Date due_on) {
        this.due_on = due_on;
    }

    public Members getCreator() {
        return creator;
    }

    public void setCreator(Members creator) {
        this.creator = creator;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public String getParser() {
        return parser;
    }

    public void setParser(String parser) {
        this.parser = parser;
    }

    @Override
    public String toString() {
        return "Milestones{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", parser='" + parser + '\'' +
                '}';
    }
}
