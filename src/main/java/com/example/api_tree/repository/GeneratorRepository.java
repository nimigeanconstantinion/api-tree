package com.example.api_tree.repository;

import com.example.api_tree.generator.models.Generator;
import com.example.api_tree.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GeneratorRepository extends JpaRepository<Generator,Long> {

    @Query(value = "select g from Generator g where g.generatedOn=?1")
    Optional<Generator> findGeneratorByDate(LocalDateTime genOn);
}
