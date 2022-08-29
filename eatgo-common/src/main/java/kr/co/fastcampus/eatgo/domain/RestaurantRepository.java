package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAll();

    List<Restaurant> findAllByAddressContainingAndCategoryId(@Param("region") String region,
                                                             @Param("categoryId") Long categoryId);

    Optional<Restaurant> findById(Long id);

    Restaurant save(Restaurant restaurant);
}
