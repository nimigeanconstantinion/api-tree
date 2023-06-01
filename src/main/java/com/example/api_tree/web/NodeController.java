package com.example.api_tree.web;

import com.example.api_tree.model.Node;
import com.example.api_tree.services.NodeServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tree")
@AllArgsConstructor
@CrossOrigin
public class NodeController {

    private NodeServices treeService;



    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public void createTree() {
        // Create the root node

        Node rootNode = treeService.createNode("Root", null);

        // Create child nodes
        Node childNode1 = treeService.createNode("Child 1", rootNode);
        Node childNode2 = treeService.createNode("Child 2", rootNode);

        // Add more child nodes as needed
        // treeService.createNode(...)

        // Add child nodes to the root node
        treeService.addChildNode(rootNode, childNode1);
        treeService.addChildNode(rootNode, childNode2);
    }


}
