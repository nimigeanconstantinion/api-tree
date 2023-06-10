package com.example.api_tree.repository;

import com.example.api_tree.model.CustomField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomFieldRepository extends JpaRepository<CustomField,String> {

    @Query(value = "select f from CustomField f where f.parentRid=?1")
    public List<CustomField> getCustomFieldsByParentRid(String s);
}
