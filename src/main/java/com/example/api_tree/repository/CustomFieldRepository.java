package com.example.api_tree.repository;

import com.example.api_tree.model.CustomField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFieldRepository extends JpaRepository<CustomField,String> {
}
