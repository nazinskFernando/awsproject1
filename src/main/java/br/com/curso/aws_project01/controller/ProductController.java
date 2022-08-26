package br.com.curso.aws_project01.controller;

import br.com.curso.aws_project01.enums.EventType;
import br.com.curso.aws_project01.model.Product;
import br.com.curso.aws_project01.repository.ProductRepository;
import br.com.curso.aws_project01.service.ProductPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/products")
public class ProductController {


    private ProductRepository productRepository;
    private ProductPublisherService productPublisherService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductPublisherService productPublisherService) {
        this.productRepository = productRepository;
        this.productPublisherService = productPublisherService;
    }

    @GetMapping
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isPresent()) {
            return new ResponseEntity<>(optProduct.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        Product productCreated = productRepository.save(product);

        productPublisherService.publishProductEvent(productCreated, EventType.PRODUCT_CREATED, "matilde");
        return new ResponseEntity<Product>(productCreated, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("id") long id){
        if(productRepository.existsById(id)){
//            product.setId(id);
            Product productUpdate = productRepository.save(product);

            productPublisherService.publishProductEvent(product, EventType.PRODUCT_UPDATE, "doralice");
            return new ResponseEntity<Product>(productUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long id){
        Optional<Product> optProduct = productRepository.findById(id);

        if (optProduct.isPresent()){

            productRepository.delete(optProduct.get());
            productPublisherService.publishProductEvent(optProduct.get(), EventType.PRODUCT_DELETED, "fefe");
            return new ResponseEntity<Product>(optProduct.get(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/bycode")
    public ResponseEntity<Product> findById(@RequestParam String code) {
        Optional<Product> optProduct = productRepository.findByCode(code);
        if (optProduct.isPresent()) {
            return new ResponseEntity<>(optProduct.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
