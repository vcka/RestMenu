package ml.penkisimtai.restMenu.repository;

import ml.penkisimtai.restMenu.model.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends PagingAndSortingRepository<MenuItem, Long> {
    public Optional<MenuItem> findByName(String name);
}
