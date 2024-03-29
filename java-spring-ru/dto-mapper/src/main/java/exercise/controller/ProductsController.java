package exercise.controller;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;
import exercise.model.Product;
import exercise.repository.ProductRepository;
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
    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> all = productRepository.findAll();
        List<ProductDTO> productDTOS = all.stream().map(product -> productMapper.mapToDto(product)).toList();
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product id %d not found".formatted(id));
        }

        Product product = optionalProduct.get();
        return ResponseEntity.ok(productMapper.mapToDto(product));
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> saveNewProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        Product product = productMapper.mapToCreateEntity(productCreateDTO);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapToDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable long id, @RequestBody ProductUpdateDTO productUpdateDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product id %d not found".formatted(id));
        }

        Product productDb = optionalProduct.get();
        Product product = productMapper.mapToEntity(productUpdateDTO, productDb);

        productRepository.save(product);

        return ResponseEntity.ok(productMapper.mapToDto(product));
    }
    // END
}
