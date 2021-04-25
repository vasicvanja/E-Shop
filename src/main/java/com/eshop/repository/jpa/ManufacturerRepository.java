package com.eshop.repository.jpa;

import com.eshop.model.Category;
import com.eshop.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    List<Manufacturer> findAllByNameLike(String text);

}
