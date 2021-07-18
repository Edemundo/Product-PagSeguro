package net.codejava.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import net.codejava.models.Product;
import net.codejava.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;


	@GetMapping("/products")
	public List<Product> list(){

    List<Product> lstProduct = service.listAll();
		return service.listAll();
	}

  @GetMapping("/products/search")
  public List<Product> listFilter(@RequestParam Optional<String> q, @RequestParam Optional<Float> min_price,
                                  @RequestParam Optional<Float> max_price){

	  List<Product> lstProduct = service.listAllFilter(q, min_price, max_price);
    return lstProduct;
  }

	@GetMapping("/products/{id}")
	public ResponseEntity<Product> get(@PathVariable Long id) {
		try {
			Product product = service.get(id);
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/products")
	public ResponseEntity<Product> add(@RequestBody Product product) {

	  try {
      service.save(product);
      return new ResponseEntity<>(HttpStatus.OK);
    }
	  catch (Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
	}


	@PutMapping("/products/{id}")
	public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long id) {

		try {
			service.update(product, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("products/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}










}
