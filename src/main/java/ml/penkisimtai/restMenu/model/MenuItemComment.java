package ml.penkisimtai.restMenu.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class MenuItemComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment may not be blank")
    private String comment;

    @ManyToOne
    private MenuItem menuItem;

    public MenuItemComment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
}
