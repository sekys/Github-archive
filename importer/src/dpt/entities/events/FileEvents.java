package dpt.entities.events;

/**
 * Created with IntelliJ IDEA.
 * User: Seky
 * Date: 22.10.2013
 * Time: 23:29
 */

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "FileEvents")
public class FileEvents implements Serializable {
    @Id
    @SerializedName("_id")
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FileEvents_ID_SEQ")
    @SequenceGenerator(schema = "PDT", name = "FileEvents_ID_SEQ", allocationSize = 1)
    private BigDecimal id;

    @Column(name = "fileName")
    private String fileName;


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileEvents{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}

