package ml.penkisimtai.restMenu.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "menu_items", uniqueConstraints={@UniqueConstraint(columnNames ={"name","description"})})
public class MenuItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "Description may not be blank")
    private String description;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "menuItem", orphanRemoval = true)
    private List<MenuItemComment> comments = new ArrayList<>();

    @OneToOne
//            (cascade = CascadeType.ALL,
//            mappedBy = "menuItem", orphanRemoval = true)
    private MenuItemFile fileName;

    public MenuItem() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuItemComment> getComments() {
        return comments;
    }

    public void addComment(MenuItemComment comment) {
        comments.add(comment);
        comment.setMenuItem(this);
    }

    public void removeComment(MenuItemComment comment) {
        comment.setMenuItem(null);
        this.comments.remove(comment);
    }

//    public void addFile(MenuItemFile menuItemFile){
//        fileName.add(menuItemFile);
//        menuItemFile.setMenuItem(this);
//    }
}
