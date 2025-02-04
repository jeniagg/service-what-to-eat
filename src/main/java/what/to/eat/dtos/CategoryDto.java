package what.to.eat.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CategoryDto implements Serializable {

    private Integer id;
    private String name;

    @JsonCreator
    public CategoryDto(@JsonProperty("id") int id,
                       @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (! (obj instanceof CategoryDto)) {
            return false;
        }
        CategoryDto other = (CategoryDto) obj;
        return this.name == other.name;
    }
}
