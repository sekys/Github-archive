package dpt.entities.events;

import com.google.gson.annotations.SerializedName;
import dpt.entities.issues.Issues;
import dpt.entities.issues.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "EVENTS")
public class Events implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SerializedName("_id")
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENTS_ID_SEQ")
    @SequenceGenerator(schema = "PDT", name = "EVENTS_ID_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "URL")
    private String url;

    @Column(name = "type")
    private String type;

    @Column(name = "actor")
    private String actor;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "issue")
    private Issues issues;

    @OneToOne
    @JoinColumn(name = "Repository_id")
    private Repository repository;

    @ManyToOne
    @JoinColumn(name = "FILEEVENTS_ID")
    private FileEvents fileEvents;

    @Column(name = "parser", length = 5)
    private String parser;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Issues getIssues() {
        return issues;
    }

    public void setIssues(Issues issues) {
        this.issues = issues;
    }

    public FileEvents getFileEvents() {
        return fileEvents;
    }

    public void setFileEvents(FileEvents file) {
        this.fileEvents = file;
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
        return "Events{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", actor='" + actor + '\'' +
                ", created_at=" + created_at +
                ", issues=" + issues +
                ", repository=" + repository +
                ", fileEvents=" + fileEvents +
                ", parser='" + parser + '\'' +
                '}';
    }
}
