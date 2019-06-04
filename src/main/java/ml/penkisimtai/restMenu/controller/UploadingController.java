package ml.penkisimtai.restMenu.controller;

import ml.penkisimtai.restMenu.model.MenuItem;
import ml.penkisimtai.restMenu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadingController {
    private MenuRepository menuRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingDir/";

    @GetMapping("/admin")
    public String uploading(Model model) {
        File file = new File(uploadingDir);
        model.addAttribute("files", file.listFiles());
        return "uploading";
    }

    @PostMapping("/api/upload")
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) {
        for(MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(uploadingDir + uploadedFile.getOriginalFilename());
            try {
                uploadedFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin";
            }
        }

        return "redirect:/admin";
    }
}
