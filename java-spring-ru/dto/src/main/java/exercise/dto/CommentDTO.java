package exercise.dto;

import lombok.Getter;
import lombok.Setter;

// BEGIN
public class CommentDTO {
    private long id;
    private String body;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
// END
