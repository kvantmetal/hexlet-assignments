package exercise.controller;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @GetMapping(path = "")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream().map(p -> productMapper.map(p)).toList();
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product by id %d not found".formatted(id));
        }

        Product product = productOptional.get();
        return ResponseEntity.ok(productMapper.map(product));
    }

    @PostMapping(path = "")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody @Valid ProductCreateDTO productCreateDTO) {
        Product product = productMapper.map(productCreateDTO);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.map(product));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable long id, @RequestBody @Valid ProductUpdateDTO productUpdateDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product by id %d not found".formatted(id));
        }
        Product product = productOptional.get();
        productMapper.update(productUpdateDTO, product);

        productRepository.save(product);
        return ResponseEntity.ok(productMapper.map(product));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        productRepository.deleteById(id);
    }

    // END
}
