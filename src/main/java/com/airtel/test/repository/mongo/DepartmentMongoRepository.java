package com.airtel.test.repository.mongo;

import com.airtel.test.entity.mongodb.DepartmentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentMongoRepository extends MongoRepository<DepartmentMongo, String> {

    public Optional<DepartmentMongo> findByName(String id);

    public Optional<List<DepartmentMongo>> findByCreatedBy(String user);
}
