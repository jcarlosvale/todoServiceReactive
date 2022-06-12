package com.study.service;

import com.study.model.ProductDocument;
import com.study.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    final private ProductRepository repositoy;

    public Mono<ProductDocument> addProduct(ProductDocument product) {
        return repositoy.save(product);
    }

    public Flux<ProductDocument> getAllProduct() {

        return repositoy.findAll();
    }

    public Mono<ProductDocument> getProductById(String name) { return repositoy.findById(name);
    }

    public Mono<ProductDocument> uptadeProduct(String name, ProductDocument productDocument) {
        return repositoy.findById(name)
                .flatMap(nameProduct -> {
                    nameProduct.setName(productDocument.getName());
                    nameProduct.setPrice(productDocument.getPrice());
                    nameProduct.setDescription(productDocument.getDescription());
                    return repositoy.save(nameProduct);
                });
    }

    public Mono<Void> deleteProduct (String name){
        return repositoy.deleteById(name);
    }
}