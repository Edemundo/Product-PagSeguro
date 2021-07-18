package net.codejava.repositorys;

import net.codejava.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{

  @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% " +
    "or p.description LIKE %?1%")
  public List<Product> findAll(String q);

  public List<Product> findAllByPriceBetween(Float min_price, Float max_price);

}
