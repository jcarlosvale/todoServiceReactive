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

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductDocument> getAllProducts(){
        return  service.getAllDocuemnts();

    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<ProductDocument>> getProductDocumentById(@PathVariable final String id){
        return
        service.getProductDocumentById(id)
                .map(productDocument -> ResponseEntity.status(HttpStatus.OK).body(productDocument))
                .switchIfEmpty(Mono.just((ResponseEntity.notFound().build())))
                        .log();
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<ProductDocument>> updateDocumentProduct(@PathVariable final String id, @RequestBody final ProductDocument document){
        return this.service.updateProduct(id, document)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDocument> addProduct(@RequestBody final ProductDocument document){
     return this.service.addProduct(document).log();
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable final String id){
        return this.service.deleteProduct(id).log()   ;
    }



}
