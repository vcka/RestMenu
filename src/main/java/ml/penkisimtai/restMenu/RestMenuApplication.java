package ml.penkisimtai.restMenu;

import ml.penkisimtai.restMenu.controller.MenuItemController;
import ml.penkisimtai.restMenu.controller.UploadingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class RestMenuApplication {

	public static void main(String[] args) {
		new File(UploadingController.uploadingDir).mkdirs();
		SpringApplication.run(RestMenuApplication.class, args);
	}

}
