package com.example.api_tree.repository;

import com.example.api_tree.model.CustomField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomFieldsRepo extends JpaRepository<CustomField,Long> {

    @Query("select a from CustomField a where a.idOwner=?1")
    List<CustomField> getCustomFieldById(Long idO);

    @Query("select f from CustomField f where f.idOwner=?1")
    List<CustomField> getFieldsByIdOwner(Long id);
}
