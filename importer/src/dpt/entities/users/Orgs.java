package dpt.entities.users;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Orgs")
public class Orgs {
    @Id
    @SerializedName("_id")
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGS_ID_SEQ")
    @SequenceGenerator(schema = "PDT", name = "ORGS_ID_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "AVATAR_URL")
    private String avatar_url;

    @Column(name = "URL")
    private String url;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Orgs{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", url='" + url + '\'' +
                ", parser='" + parser + '\'' +
                '}';
    }
}
