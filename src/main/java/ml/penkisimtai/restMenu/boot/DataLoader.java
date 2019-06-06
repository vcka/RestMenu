package ml.penkisimtai.restMenu.boot;

import ml.penkisimtai.restMenu.model.MenuItem;
import ml.penkisimtai.restMenu.model.MenuItemComment;
import ml.penkisimtai.restMenu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private MenuRepository menuRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        MenuItem menuItem = new MenuItem();

        menuItem.setName("Kotletukas");
        menuItem.setDescription("Močiutės pakepti, iš kiaulienos faršo");

        menuRepository.save(menuItem);

        MenuItem menuItem1 = new MenuItem();

        menuItem1.setName("Krabų salotos");
        menuItem1.setDescription("VICI krabų lazdelių salotos.");

        menuRepository.save(menuItem1);
    }
}
