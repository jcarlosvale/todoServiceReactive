package com.study.repository;

import com.study.domain.TodoInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TodoInfoRepository extends ReactiveMongoRepository<TodoInfo, String> {

}
