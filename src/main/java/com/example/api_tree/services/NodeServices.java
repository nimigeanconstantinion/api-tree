package com.example.api_tree.services;

import com.example.api_tree.model.Node;
import com.example.api_tree.repository.NodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class NodeServices {


    private NodeRepository nodeRepository;

    public Node createNode(String label, Node parent) {
        Node newNode = new Node();
        newNode.setLabel(label);
        newNode.setParinte(parent);
        return nodeRepository.save(newNode);
    }

    public void addChildNode(Node parent, Node child) {
        parent.getSubordinates().add(child);
        child.setParinte(parent);
        nodeRepository.save(parent);
    }

}
