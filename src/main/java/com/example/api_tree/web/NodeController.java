package com.example.api_tree.web;

import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Node;
import com.example.api_tree.repository.CustomFieldRepository;
import com.example.api_tree.repository.NodeRepository;
import com.example.api_tree.services.NodeServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tree")
@AllArgsConstructor
@CrossOrigin
public class NodeController {

    private NodeServices treeService;
    private NodeRepository nodeRepository;
    private CustomFieldRepository customFieldRepository;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/testadd")
    public void createTree() {
//        // Create the root node
//
//        Node rootNode = treeService.createNode("Root", null);
//        CustomField cf=new CustomField();
//        cf.setCustomKey("camp1");
//        cf.setType("string");
//        cf.setValue("ldsklksdlkldslskd");
//
        Node root=nodeRepository.findNodeByLabel("Root").get();
//        root.addCustomField(cf);
//
//        nodeRepository.save(root);
//
//        // Create child nodes
//        //Node childNode1 = treeService.createNode("Child 1", rootNode);
//        //Node childNode2 = treeService.createNode("Child 2", rootNode);
//
//        // Add more child nodes as needed
//        // treeService.createNode(...)
//
//        // Add child nodes to the root node
//        //treeService.addChildNode(rootNode, childNode1);
//        //treeService.addChildNode(rootNode, childNode2);
        //         Create child nodes
        Node childNode1 = treeService.createNode("Child 1", root);
        Node childNode2 = treeService.createNode("Child 2", root);
        treeService.addChildNode(root,childNode1);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<Node> getAll(){
        return treeService.getTree();
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addf/{idNod}")
    public List<CustomField> addField(@PathVariable String idNod,@RequestBody CustomField camp){
        Node nod=nodeRepository.findById(idNod).get();
        treeService.addCustomField(nod,camp);
        return treeService.getFieldsOfNode(idNod);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delf/{idNod}/{idField}")
    public List<CustomField> delField(@PathVariable String idNod,@PathVariable String idField){

        treeService.delField(idNod,idField);
        return treeService.getFieldsOfNode(idNod);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delchild/{idP}/{idC}")
    public List<Node> delChild(@PathVariable String idP,@PathVariable String idC){

        treeService.removeChildNode(idP,idC);
        return treeService.getTree();

    }

}
