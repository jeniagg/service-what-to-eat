package what.to.eat.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicUserDto implements Serializable {

    private String username;
    private String email;

    @JsonCreator
    public BasicUserDto(@JsonProperty(value = "username", required = true) String username,
                   @JsonProperty(value = "email", required = true) String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (! (obj instanceof BasicUserDto)) {
            return false;
        }
        BasicUserDto other = (BasicUserDto) obj;
        return this.username == other.username;
    }

}
