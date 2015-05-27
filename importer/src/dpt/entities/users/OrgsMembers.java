package dpt.entities.users;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "OrgsMembers")
public class OrgsMembers {
    @Id
    @SerializedName("_id")
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGSMEMBERS_ID_SEQ")
    @SequenceGenerator(schema = "PDT", name = "ORGSMEMBERS_ID_SEQ", allocationSize = 1)
    private BigDecimal id;

    @ManyToOne
    @JoinColumn(name = "Members_id")
    private Members member;

    @ManyToOne
    @JoinColumn(name = "Orgs_id")
    private Orgs org;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Members getMember() {
        return member;
    }

    public void setMember(Members member) {
        this.member = member;
    }

    public Orgs getOrg() {
        return org;
    }

    public void setOrg(Orgs orgs) {
        this.org = orgs;
    }

    @Override
    public String toString() {
        return "OrgsMembers{" +
                "id=" + id +
                '}';
    }
}
