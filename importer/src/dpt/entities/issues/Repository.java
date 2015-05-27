package dpt.entities.issues;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Seky
 * Date: 23.10.2013
 * Time: 15:10
 */

@Entity
@Table(name = "Repository")
public class Repository implements Serializable {

    @Id
    @SerializedName("_id")
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPOSITORY_ID_SEQ")
    @SequenceGenerator(schema = "PDT", name = "REPOSITORY_ID_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "watchers")
    private BigDecimal watchers;

    @Column(name = "pushed_at")
    @Temporal(TemporalType.DATE)
    private Date pushed_at;

    @Column(name = "organization")
    private String organization;

    @Column(name = "has_downloads")
    private Boolean has_downloads;

    @Column(name = "has_issues")
    private Boolean has_issues;

    @Column(name = "forks")
    private BigDecimal forks;

    @Column(name = "fork")
    private Boolean fork;

    @Column(name = "language")
    private String language;

    @Column(name = "URL")
    private String url;

    @SerializedName("size")
    @Column(name = "sizee")
    private BigDecimal size;

    @SerializedName("private")
    @Column(name = "privatee")
    private Boolean privatee;

    @Column(name = "has_wiki")
    private Boolean has_wiki;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(name = "name")
    private String name;

    @Column(name = "owner")
    private String owner;

    @Column(name = "open_issues")
    private BigDecimal open_issues;

    @Column(name = "parser", length = 5)
    private String parser;

    public String getParser() {
        return parser;
    }

    public void setParser(String parser) {
        this.parser = parser;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getWatchers() {
        return watchers;
    }

    public void setWatchers(BigDecimal watchers) {
        this.watchers = watchers;
    }

    public Date getPushed_at() {
        return pushed_at;
    }

    public void setPushed_at(Date pushed_at) {
        this.pushed_at = pushed_at;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Boolean getHas_downloads() {
        return has_downloads;
    }

    public void setHas_downloads(Boolean has_downloads) {
        this.has_downloads = has_downloads;
    }

    public Boolean getHas_issues() {
        return has_issues;
    }

    public void setHas_issues(Boolean has_issues) {
        this.has_issues = has_issues;
    }

    public BigDecimal getForks() {
        return forks;
    }

    public void setForks(BigDecimal forks) {
        this.forks = forks;
    }

    public Boolean getFork() {
        return fork;
    }

    public void setFork(Boolean fork) {
        this.fork = fork;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public Boolean getPrivatee() {
        return privatee;
    }

    public void setPrivatee(Boolean privatee) {
        this.privatee = privatee;
    }

    public Boolean getHas_wiki() {
        return has_wiki;
    }

    public void setHas_wiki(Boolean has_wiki) {
        this.has_wiki = has_wiki;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getOpen_issues() {
        return open_issues;
    }

    public void setOpen_issues(BigDecimal open_issues) {
        this.open_issues = open_issues;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", watchers=" + watchers +
                ", pushed_at=" + pushed_at +
                ", organization='" + organization + '\'' +
                ", has_downloads=" + has_downloads +
                ", has_issues=" + has_issues +
                ", forks=" + forks +
                ", fork=" + fork +
                ", language='" + language + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                ", privatee=" + privatee +
                ", has_wiki=" + has_wiki +
                ", created_at=" + created_at +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", open_issues=" + open_issues +
                ", parser='" + parser + '\'' +
                '}';
    }
}

