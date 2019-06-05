package ml.penkisimtai.restMenu.controller;

import ml.penkisimtai.restMenu.exception.ResourceException;
import ml.penkisimtai.restMenu.model.MenuItemComment;
import ml.penkisimtai.restMenu.model.MenuItem;
import ml.penkisimtai.restMenu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class MenuItemController {
    private MenuRepository menuRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @CrossOrigin
    @GetMapping("/api/params")
    public Object userName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }

    @CrossOrigin
    @GetMapping("/api/items")
    public Iterable<MenuItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/api/items/{id}")
    public Optional<MenuItem> getCourse(@PathVariable long id) {
        return menuRepository.findById(id);
    }

    @DeleteMapping("/api/remove/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable long id) {

        Optional<MenuItem> menuItem = menuRepository.findById(id);

        if (menuItem.isPresent()) {
            menuRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/edit/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable long id, @RequestBody MenuItem menuItem) {

        Optional<MenuItem> menuItemUpdated = menuRepository.findById(id);
        if (menuItemUpdated.isPresent()) {
            menuItem.setId(id);
            menuRepository.save(menuItem);
            return new ResponseEntity<MenuItem>(menuItem, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @PutMapping("/api/comment/{id}")
    public ResponseEntity<MenuItem> updateMenuItemComment(@PathVariable long id, @RequestBody MenuItemComment itemComment) {

        Optional<MenuItem> menuItemUpdated = menuRepository.findById(id);
        if (menuItemUpdated.isPresent()) {
            MenuItem menuItem = menuItemUpdated.get();
            menuItem.addComment(itemComment);
            menuRepository.save(menuItem);
            return new ResponseEntity<MenuItem>(menuItem, HttpStatus.OK);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping("/api/add")
    public ResponseEntity<Void> createMenuItem(@RequestBody MenuItem menuItem) {
        try {
            MenuItem createdMenuItem = menuRepository.save(menuItem);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(createdMenuItem.getId())
                    .toUri();

            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            throw new ResourceException(HttpStatus.CONFLICT, "Resource allready exists");
        }

    }

    @GetMapping("/logoff")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect("/");
        } catch (Exception e) {
            //  e.printStackTrace();

        }
    }

}
