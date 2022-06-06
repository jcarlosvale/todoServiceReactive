package com.study.model.repository;

import com.study.model.ProductDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductDocument, String> {
}
