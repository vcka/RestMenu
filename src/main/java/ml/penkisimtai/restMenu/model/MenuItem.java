package ml.penkisimtai.restMenu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "menu_items", uniqueConstraints={@UniqueConstraint(columnNames ={"name","description"})})
@ApiModel(description = "All details about the Menu item. ")
public class MenuItem{

    @ApiModelProperty(notes = "The database generated menu item ID.")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "The menu item name.")
    @NotBlank(message = "Name may not be blank")
    private String name;

    @ApiModelProperty(notes = "The menu item description.")
    @NotBlank(message = "Description may not be blank")
    private String description;

    @ApiModelProperty(notes = "The menu item comments.")
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "menuItem", orphanRemoval = true)
    private List<MenuItemComment> comments = new ArrayList<>();

    @ApiModelProperty(notes = "The menu item image.")
    @OneToOne
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
