package com.example.api_tree.web;

import com.example.api_tree.dto.NodeDTO;
import com.example.api_tree.dto.SerialDTO;
import com.example.api_tree.generator.GenaratorCounterType;
import com.example.api_tree.generator.models.Generator;
import com.example.api_tree.generator.service.ComandSerialService;
import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Node;
import com.example.api_tree.repository.CustomFieldRepository;
import com.example.api_tree.repository.GeneratorRepository;
import com.example.api_tree.repository.NodeRepository;
import com.example.api_tree.services.NodeServices;
import com.example.api_tree.generator.service.SerialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tree")
@AllArgsConstructor
@CrossOrigin
public class NodeController {

    private NodeServices treeService;
    private NodeRepository nodeRepository;
    private CustomFieldRepository customFieldRepository;
    private ComandSerialService comandSerialSerivce = new SerialService();
    private GeneratorRepository generatorRepository;
//    private SerialService serialService=new SerialService();

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
        Node root = nodeRepository.findNodeByLabel("Root").get();
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
        treeService.addChildNode(root, childNode1);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<Node> getAll() {
        return treeService.getTree();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/dtoall")
    public List<NodeDTO> getAllDTO() {

        return treeService.getTreeDTO();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addf/{idNod}")
    public List<CustomField> addField(@PathVariable String idNod, @RequestBody CustomField camp) {
        Node nod = nodeRepository.findById(idNod).get();
        treeService.addCustomField(nod, camp);
        return treeService.getFieldsOfNode(idNod);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delf/{idNod}/{idField}")
    public List<CustomField> delField(@PathVariable String idNod, @PathVariable String idField) {

        treeService.delField(idNod, idField);
        return treeService.getFieldsOfNode(idNod);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delchild/{idP}/{idC}")
    public List<Node> delChild(@PathVariable String idP, @PathVariable String idC) {

        treeService.removeChildNode(idP, idC);
        return treeService.getTree();

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delnode/{idNode}")
    public List<Node> delNode(@PathVariable String idNode) {

        Node nod = treeService.getNodeByID(idNode);
        if (nod != null) {
            treeService.removeNode(idNode);
            generatorRepository.delete(nod.getGeneratedID());
        }
        return treeService.getTree();

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/root/{idR}")
    public Node getRoot(@PathVariable String idR) {

        return nodeRepository.findById(idR).get();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addRoot")
    public void addRoot(@RequestBody Node root) {

        Generator gn = root.getGeneratedID();
        System.out.println(gn.getGeneratedOn().plus(2, ChronoUnit.DAYS));

        generatorRepository.saveAndFlush(gn);
        Generator gnn = generatorRepository.findGeneratorByDate(gn.getGeneratedOn()).get();
        System.out.println("-----------------------------");

        System.out.println(gnn);
        System.out.println("-----------------------------");

        root.setGeneratedID(generatorRepository.findGeneratorByDate(gn.getGeneratedOn()).get());
        nodeRepository.saveAndFlush(root);


    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updnode")
    public Node updNode(@RequestBody Node nod) {
        Node parinte = treeService.getNodeByID(nod.getId()).getParinte();
        nod.setParinte(parinte);
        treeService.updateNode(parinte, nod);
        return treeService.getNodeByID(nod.getId());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addchild/{labelP}")
    public ResponseEntity<NodeDTO> addChild(@PathVariable String labelP, @RequestBody Node child) {


        if (nodeRepository.findNodeByLabel(labelP).isPresent()) {
            System.out.println("------------- am gasit-----");
            Node parent = nodeRepository.findNodeByLabel(labelP).get();
            parent.addSubordonate(child);
            nodeRepository.saveAndFlush(parent);

        } else {
            System.out.println("------------- nu eeee   -----");

            Generator gn = child.getGeneratedID();
            if (gn != null) {
                generatorRepository.saveAndFlush(gn);
                Generator gnn = generatorRepository.findGeneratorByDate(gn.getGeneratedOn()).get();


                child.setGeneratedID(generatorRepository.findGeneratorByDate(gn.getGeneratedOn()).get());
            }
            child.setParinte(null);
            nodeRepository.saveAndFlush(child);
        }
        Node raspuns = nodeRepository.findNodeByLabel(child.getLabel()).get();

        return new ResponseEntity<>(new NodeDTO(raspuns.getId(), raspuns.getLabel()), HttpStatus.OK);


    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addchild2parent/{labelP}")
    public ResponseEntity<Node> addChildtonode(@PathVariable String labelP, @RequestBody Node child) {

        if(labelP.equals("null")&&nodeRepository.findNodeByLabel(child.getLabel()).isEmpty()){
            System.out.println("-----------E ROOT");
            child.setParinte(null);
            nodeRepository.saveAndFlush(child);
            Node chld=treeService.getNodeEquals(child.getLabel());
            return new ResponseEntity<Node>(chld,HttpStatus.OK);

        }else{
            Node parent=treeService.getNodeEquals(labelP);
            if(parent!=null&&nodeRepository.findNodeByLabel(child.getLabel()).isEmpty()){
                parent.addSubordonate(child);
                nodeRepository.saveAndFlush(parent);
                return new ResponseEntity<Node>(parent,HttpStatus.OK);  }



        }
        return new ResponseEntity<>((Node) null,HttpStatus.NOT_MODIFIED);


    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getbylabel/{labelP}")
    public ResponseEntity<Node> addChildToParent(@PathVariable String labelP, @RequestBody Node child) {
        if (treeService.getNodeEquals(labelP) != null) {
            return new ResponseEntity<Node>(treeService.getNodeEquals(labelP), HttpStatus.OK);
        }
        return new ResponseEntity<Node>((Node) null, HttpStatus.NO_CONTENT);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getnodebylabel/{lbl}")
    public Node getNodeByLabel(@PathVariable String lbl) {
        Optional<Node> nod = nodeRepository.findNodeByLabel(lbl);
        if (nod.isPresent()) {
            return nod.get();
        }

        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getnextid/{type}/{crt}")
    public ResponseEntity<SerialDTO> getNextSerial(@PathVariable String type, @PathVariable String crt) {
        String serial = comandSerialSerivce.getNextSerial(crt, type);
        return new ResponseEntity<>(new SerialDTO(serial), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/dropnode/{idC}/{idP}")
    public boolean dropNode(@PathVariable String idC, @PathVariable String idP) {


        Node item=treeService.getNodeByID(idC);
        Node drParent=treeService.getNodeByID(idP);

        if(item.getParinte()!=null){
            if(drParent!=null){
                Node oldP=item.getParinte();
                oldP.removeSubordonate(item);
                drParent.addSubordonate(item);
                nodeRepository.saveAndFlush(oldP);
                nodeRepository.saveAndFlush(drParent);
                return true;
            }else{
                item.setParinte(null);
                nodeRepository.saveAndFlush(item);
                return true;
            }

        }


        return false;

    }

}
