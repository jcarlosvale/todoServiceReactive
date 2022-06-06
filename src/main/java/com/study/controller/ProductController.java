package com.study.controller;

import com.study.model.ProductDocument;
import com.study.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ProductController {

    private final ProductService service;

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDocument> addProduct(@RequestBody final ProductDocument product) {

        return this.service.addProduct(product).log();
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductDocument> getAllProduct(){

        return service.getAllProduct().log();
    }

    @GetMapping("/products/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<ProductDocument>> getProductById(@PathVariable final String name){
        return service.getProductById(name)
                .map(nameProduct -> ResponseEntity.status(HttpStatus.OK).body(nameProduct))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @PutMapping("/products/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<ProductDocument>> uptadeProduct (@PathVariable final String name, @RequestBody ProductDocument productDocument){
        return service.uptadeProduct(name, productDocument)
                .map(nameProduct -> ResponseEntity.status(HttpStatus.CREATED).body(nameProduct))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/products/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct (@PathVariable final String name){
        return service.deleteProduct(name).log();
    }

}
