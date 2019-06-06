package ml.penkisimtai.restMenu.controller;

import io.swagger.annotations.*;
import ml.penkisimtai.restMenu.exception.ResourceException;
import ml.penkisimtai.restMenu.model.MenuItemComment;
import ml.penkisimtai.restMenu.model.MenuItem;
import ml.penkisimtai.restMenu.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "Restaurant menu Management System", description = "Restaurant menu Management system example project")
public class MenuItemController {

    private static final Logger logger = LoggerFactory.getLogger("ExceptionHandlerAdvice.class");

    private MenuRepository menuRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @ApiOperation(value = "View session params", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/api/params")
    public Object userName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }

    @ApiOperation(value = "View a list of available menu items", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/api/items")
    public Iterable<MenuItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    @ApiOperation(value = "Get an menu item by Id")
    @GetMapping("/api/items/{id}")
    public Optional<MenuItem> getCourse(@ApiParam(value = "Menu item id from which menu item object will retrieve", required = true) @PathVariable(value = "id") long id) {
        try {
            return menuRepository.findById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Check your request");
        }
    }

    @ApiOperation(value = "Delete a menu item")
    @DeleteMapping("/api/remove/{id}")
    public ResponseEntity<Void> deleteMenuItem(
            @ApiParam(value = "Menu item Id from which menu item object will delete from database table", required = true) @PathVariable(value = "id") long id) {

        Optional<MenuItem> menuItem = menuRepository.findById(id);

        if (menuItem.isPresent()) {
            menuRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        logger.error("Menu item to delete not found!id: " + id);
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Update a menu item")
    @PutMapping("/api/edit/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(
            @ApiParam(value = "Menu item Id to update menu item object", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "Update menu item object", required = true) @RequestBody MenuItem menuItem) {

        Optional<MenuItem> menuItemUpdated = menuRepository.findById(id);
        if (menuItemUpdated.isPresent()) {
            menuItem.setId(id);
            menuRepository.save(menuItem);
            return new ResponseEntity<MenuItem>(menuItem, HttpStatus.OK);
        }
        logger.error("Menu item to update not found! id: " + id);
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Add/update a menu item comments")
    @PutMapping("/api/comment/{id}")
    public ResponseEntity<MenuItem> updateMenuItemComment(
            @ApiParam(value = "Menu item comment Id to add/update menu item comment object", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "Add/update menu item comment object", required = true) @RequestBody MenuItemComment itemComment) {

        Optional<MenuItem> menuItemUpdated = menuRepository.findById(id);
        if (menuItemUpdated.isPresent()) {
            MenuItem menuItem = menuItemUpdated.get();
            menuItem.addComment(itemComment);
            menuRepository.save(menuItem);
            return new ResponseEntity<MenuItem>(menuItem, HttpStatus.CREATED);
        }
        logger.error("Menu item to delete not found!");
        return ResponseEntity.unprocessableEntity().build();
    }

    @ApiOperation(value = "Add a menu item")
    @PostMapping("/api/add")
    public ResponseEntity<String> createMenuItem(@ApiParam(value = "Menu item object store in database table", required = true) @RequestBody MenuItem menuItem) {
        try {
            menuRepository.save(menuItem);
            return new ResponseEntity<String>("Menu item saved", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResourceException(HttpStatus.CONFLICT, "Resource allready exists");
        }

    }

    @ApiOperation(value = "Logout from the system", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
