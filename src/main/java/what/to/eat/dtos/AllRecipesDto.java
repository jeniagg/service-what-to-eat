package what.to.eat.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AllRecipesDto implements Serializable {
    private Integer id;
    private String name;

    @JsonCreator
    public AllRecipesDto(@JsonProperty("id") int id,
                     @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
