package net.codejava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.codejava.models.Product;
import net.codejava.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	private final ProductRepository repo;

  public ProductService(ProductRepository repo) {
    this.repo = repo;
  }

  public List<Product> listAll(){

		return repo.findAll();
	}

	public List<Product> listAllFilter(Optional<String> q, Optional<Float> min_price, Optional<Float> max_price){

    String optionalQValue = q.orElse(null);
    Float optionalMinValue = min_price.orElse(null);
    Float optionalMaxValue = max_price.orElse(null);

    if(optionalQValue == null){
      optionalQValue = "";
    }
    if(optionalMinValue == null){
      optionalMinValue = 0.0f;
    }
    if(optionalMaxValue == null){
      optionalMaxValue = 9999999.99f;
    }

    List<Product> lstProducts = repo.findAll(optionalQValue);
    List<Product> lstProdutosByPrice = repo.findAllByPriceBetween(optionalMinValue,optionalMaxValue);

    List<Product> lstProdFinal = new ArrayList<Product>();

    for (Product prodFinal : lstProducts) {
      if(lstProdutosByPrice.contains(prodFinal)) {
        lstProdFinal.add(prodFinal);
      }
    }

    System.out.println(lstProducts);
    System.out.println(lstProdutosByPrice);
    System.out.println(lstProdFinal);
    return lstProdFinal;
  }

	public void save(Product product) {

		repo.save(product);
	}

	public void update(Product product, Long id) {
    Product productDb = repo.findById(id).get();
    productDb.setName(product.getName());
    productDb.setDescription(product.getDescription());
    productDb.setPrice(product.getPrice());
		repo.save(product);
	}

	public Product get(Long id) {
		return repo.findById(id).get();
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
