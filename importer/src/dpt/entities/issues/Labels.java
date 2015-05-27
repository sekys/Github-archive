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
@Table(name = "LABELS")
public class Labels implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SerializedName("_id")
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LABELS_ID_SEQ")
    @SequenceGenerator(schema = "PDT", name = "LABELS_ID_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "URL")
    private String url;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COLOR")
    private String color;

    @ManyToOne
    @JoinColumn(name = "ISSUES_ID")
    private Issues issue;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Issues getIssue() {
        return issue;
    }

    public void setIssue(Issues issue) {
        this.issue = issue;
    }

    @Override
    public String toString() {
        return "Labels{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", issue=" + issue +
                ", parser='" + parser + '\'' +
                '}';
    }
}
