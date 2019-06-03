package ml.penkisimtai.restMenu.controller;

import ml.penkisimtai.restMenu.model.MenuItemComment;
import ml.penkisimtai.restMenu.model.MenuItem;
import ml.penkisimtai.restMenu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class MenuItemController {
    private MenuRepository menuRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping("/api/menu")
    public Iterable<MenuItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    @GetMapping("/api/menu/{id}")
    public Optional<MenuItem> getCourse(@PathVariable long id) {
        return menuRepository.findById(id);
    }

    @DeleteMapping("/api/menu/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable long id) {

        Optional<MenuItem> menuItem = menuRepository.findById(id);

        if (menuItem.isPresent()) {
            menuRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/menu/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable long id, @RequestBody MenuItem menuItem) {

        Optional<MenuItem> menuItemUpdated = menuRepository.findById(id);
        if (menuItemUpdated.isPresent()) {
            menuItem.setId(id);
            menuRepository.save(menuItem);
            return new ResponseEntity<MenuItem>(menuItem, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/menu/{id}/comment")
    public ResponseEntity<MenuItem> updateMenuItemComment(@PathVariable long id, @RequestBody MenuItemComment itemComment) {

        Optional<MenuItem> menuItemUpdated = menuRepository.findById(id);
        if (menuItemUpdated.isPresent()) {
            MenuItem menuItem = menuItemUpdated.get();
            menuItem.addComment(itemComment);
            menuRepository.save(menuItem);
            return new ResponseEntity<MenuItem>(menuItem, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/menu")
    public ResponseEntity<Void> createMenuItem(@RequestBody MenuItem menuItem) {

        MenuItem createdMenuItem = menuRepository.save(menuItem);

        // Location
        // Get current resource url
        /// {id}
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdMenuItem.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
