package what.to.eat.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserDto {

    private String oldPassword;
    private String password;
    private String repeatPassword;
    private String email;

    @JsonCreator
    public UpdateUserDto(@JsonProperty(value = "oldPassword", required = true) String oldPassword,
                         @JsonProperty(value = "password", required = true) String password,
                         @JsonProperty(value = "repeatPassword", required = true) String repeatPassword,
                         @JsonProperty(value = "email", required = true) String email) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
