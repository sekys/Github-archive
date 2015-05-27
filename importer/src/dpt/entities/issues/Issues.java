package dpt.entities.issues;

import com.google.gson.annotations.SerializedName;
import dpt.entities.users.Members;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * dpt.entities.Repository: Seky
 * Date: 2.10.2013
 * Time: 16:02
 */

@Entity
@Table(name = "Issues")
public class Issues implements Serializable {

    @Id
    @SerializedName("_id")
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISSUES_ID_SEQ")
    @SequenceGenerator(schema = "PDT", name = "ISSUES_ID_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "html_url")
    private String html_url;

    @Column(name = "comments_url")
    private String comments_url;

    @Column(name = "url")
    private String url;

    @SerializedName("number")
    @Column(name = "numberr")
    private String number;

    @Column(name = "state")
    private String state;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "comments")
    private BigDecimal comments;

    @Column(name = "closed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closed_at;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "ASSIGNEE")
    private Members assignee;

    @SerializedName("user")
    @ManyToOne
    @JoinColumn(name = "userr")
    private Members user;

    @ManyToOne
    @JoinColumn(name = "closed_by")
    private Members closed_by;

    @ManyToOne
    @JoinColumn(name = "milestone")
    private Milestones milestone;

    @Transient
    private List<Labels> labels;

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

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public BigDecimal getComments() {
        return comments;
    }

    public void setComments(BigDecimal comments) {
        this.comments = comments;
    }

    public Date getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(Date closed_at) {
        this.closed_at = closed_at;
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

    public Members getAssignee() {
        return assignee;
    }

    public void setAssignee(Members assignee) {
        this.assignee = assignee;
    }

    public Members getUser() {
        return user;
    }

    public void setUser(Members user) {
        this.user = user;
    }

    public Members getClosed_by() {
        return closed_by;
    }

    public void setClosed_by(Members closed_by) {
        this.closed_by = closed_by;
    }

    public Milestones getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestones milestone) {
        this.milestone = milestone;
    }

    public List<Labels> getLabels() {
        return labels;
    }

    public void setLabels(List<Labels> labels) {
        this.labels = labels;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public String getParser() {
        return parser;
    }

    public void setParser(String parser) {
        this.parser = parser;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String toString() {
        return "Issues{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", parser='" + parser + '\'' +
                '}';
    }

}
