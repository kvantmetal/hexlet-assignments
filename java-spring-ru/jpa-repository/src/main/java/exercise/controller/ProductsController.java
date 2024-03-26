package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping("")
    public ResponseEntity<List<Product>> findProducts(@RequestParam(name = "min", defaultValue = "") Long priceLowBound,
                                                      @RequestParam(name = "max", defaultValue = "") Long priceUpBound) {
        var sort = Sort.by(Sort.Order.asc("price"));
        if (priceLowBound == null && priceUpBound == null) {
            return ResponseEntity.ok(productRepository.findAll(sort));
        }
        if (priceLowBound != null && priceUpBound != null) {
            return ResponseEntity.ok(productRepository.findByPriceBetween(priceLowBound, priceUpBound, sort));
        }
        if (priceLowBound != null) {
            return ResponseEntity.ok(productRepository.findByPriceBetween(priceLowBound, Integer.MAX_VALUE, sort));
        }
        return ResponseEntity.ok(productRepository.findByPriceBetween(0, priceUpBound, sort));
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
