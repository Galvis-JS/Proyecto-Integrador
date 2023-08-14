package coworking.digitalBooking.Repository;

import coworking.digitalBooking.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByIdCategory(Long idCategory);
}

