package com.example.api_tree.repository;

import com.example.api_tree.ApiTreeApplication;
import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiTreeApplication.class)

class NodeRepositoryTest {
    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    CustomFieldRepository customFieldRepository;

    @Test
    @Transactional
    void testAddNod() {

        Node parentNode = new Node();
        parentNode.setLabel("Parent Node");
        parentNode.setDescriere("Description of the parent node");
        parentNode.setCfields(new ArrayList<>());

        List<Node> subordinates = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Node subordinateNode = new Node();
            subordinateNode.setLabel("Subordinate Node " + i);
            subordinateNode.setDescriere("Description of subordinate node " + i);
            subordinateNode.setCfields(new ArrayList<>());

            subordinates.add(subordinateNode);
        }

        parentNode.setSubordinates(subordinates);

        nodeRepository.save(parentNode);


    }



}