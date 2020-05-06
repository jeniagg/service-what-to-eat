package what.to.eat.entities;

import javax.persistence.*;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "steps")
    private String steps;

    @Column(name = "cooking_method_id")
    private Integer cookingMethodId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "comment")
    private String comment;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Integer getCookingMethodId() {
        return cookingMethodId;
    }

    public void setCookingMethodId(Integer cookingMethodId) {
        this.cookingMethodId = cookingMethodId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
