package com.example.api_tree.repository;

import com.example.api_tree.model.Node;
import jakarta.persistence.ElementCollection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends JpaRepository<Node,String> {


    @EntityGraph(attributePaths = {"cfields","subordinates"},type = EntityGraph.EntityGraphType.LOAD)
    Optional<Node> findById(String id);

    @EntityGraph(attributePaths = {"cfields", "subordinates"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Node> findAll();



    @Query(value = "select n from Node n where n.label=?1")
    Optional<Node> findNodeByLabel(String s);

    @Query(value = "select n from Node n where n.parinte=?1")
    List<Node> getSubordinatesByParentId(String id);

    @Query(value = "select n from Node n where n=:node")
    Optional<Node> getNodeByEquals(@Param("node") Node node);

}
