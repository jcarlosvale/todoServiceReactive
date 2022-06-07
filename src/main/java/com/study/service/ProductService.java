package com.study.service;

import com.study.model.ProductDocument;
import com.study.model.UserDocument;
import com.study.model.repository.ProductRepository;
import com.study.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.Document;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository repository;


    public Mono<ProductDocument> addProduct(final ProductDocument document) {
        return repository.save(document);
    }

    public Flux<ProductDocument> getAllDocuments() {
        return repository.findAll();
    }

    public Mono<ProductDocument> getDocumentById(final String id) {
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

    public Mono<Void> deleteProduct(String id) {
        return repository.deleteById(id);
    }
}
