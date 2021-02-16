package upp.dto;

import javax.persistence.Column;
import java.io.Serializable;

public class BookDTO implements Serializable {

    private Long id;
    private String name;
    private String link;

    public BookDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
