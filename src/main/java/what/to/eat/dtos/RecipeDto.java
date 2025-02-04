package what.to.eat.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeDto implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private String username;
    private String steps;
    private CookingMethodEnum cookingMethod;
    private CategoryEnum category;
    private String comment;

    @JsonCreator
    public RecipeDto(@JsonProperty("id") Integer id,
                     @JsonProperty(value = "name", required = true) String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("username") String username,
                     @JsonProperty("steps") String steps,
                     @JsonProperty("cookingMethod") CookingMethodEnum cookingMethod,
                     @JsonProperty("category") CategoryEnum category,
                     @JsonProperty("comment") String comment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.username = username;
        this.steps = steps;
        this.cookingMethod = cookingMethod;
        this.category = category;
        this.comment = comment;

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

    public Integer getId() {
        return this.id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCookingMethod(CookingMethodEnum cookingMethod) {
        this.cookingMethod = cookingMethod;
    }

    public CookingMethodEnum getCookingMethod() {
        return cookingMethod;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getSteps() {
        return steps;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
