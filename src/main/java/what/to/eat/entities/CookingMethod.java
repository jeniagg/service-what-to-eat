package what.to.eat.entities;

import javax.persistence.*;

@Entity
@Table(name = "cooking_method")
public class CookingMethod {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;

    @Column(name = "name")
    public String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
