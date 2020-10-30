package com.awado.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.awado.model.BikeModel;

@Repository
public interface BikeRepository extends CrudRepository<BikeModel, String> {
    
}
