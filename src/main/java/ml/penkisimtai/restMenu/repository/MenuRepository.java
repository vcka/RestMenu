package ml.penkisimtai.restMenu.repository;

import ml.penkisimtai.restMenu.model.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<MenuItem, Long> {
}