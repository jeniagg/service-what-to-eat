package what.to.eat.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RecipeDto {

    private Integer id;
    private String name;
    private String description;
    private Integer userId;
    private String steps;
    private Integer cookingMethodId;
    private CategoryDto category;
    private String comment;

    @JsonCreator
    public RecipeDto(@JsonProperty("id") int id,
                     @JsonProperty("name") String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("userId") Integer userId,
                     @JsonProperty("steps") String steps,
                     @JsonProperty("cookingMethodId") Integer cookingMethodId,
                     @JsonProperty("category") CategoryDto category,
                     @JsonProperty("comment") String comment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.steps = steps;
        this.cookingMethodId = cookingMethodId;
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

    public int getId() {
        return this.id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setCategoryId(CategoryDto category) {
        this.category = category;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCookingMethodId(Integer cookingMethodId) {
        this.cookingMethodId = cookingMethodId;
    }

    public Integer getCookingMethodId() {
        return cookingMethodId;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getSteps() {
        return steps;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
