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

    private final ProductRepository repository;

    public Flux<ProductDocument> getAllDocuemnts() {

        return repository.findAll();
    }


    public Mono<ProductDocument> getProductDocumentById(final String id) {
        return repository.findById(id);

    }

    public Mono<ProductDocument> updateProduct(String id, ProductDocument document) {
    return repository
            .findById(id)
            .flatMap(productDocument -> {
                productDocument.setName(document.getName());
                productDocument.setPrice(document.getPrice());
                productDocument.setDescription(document.getDescription());
                return repository.save(productDocument);
            });

    }

    public Mono<ProductDocument> addProduct(final ProductDocument document) {
        return repository.save(document);
    }

    public Mono<Void> deleteProduct(String id) {
        return repository.deleteById(id);
    }
}
