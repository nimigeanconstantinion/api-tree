package com.example.api_tree.services;

import com.example.api_tree.dto.NodeDTO;
import com.example.api_tree.generator.models.Generator;
import com.example.api_tree.generator.service.SerialService;
import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Node;
import com.example.api_tree.repository.CustomFieldRepository;
import com.example.api_tree.repository.NodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class NodeServices {


    private NodeRepository nodeRepository;
    private CustomFieldRepository customFieldRepository;
    private SerialService serialService;

    public Node createNode(String label, Node parent) {
        Node newNode = new Node();
        newNode.setLabel(label);
        newNode.setParinte(parent);
        newNode.setCfields(new HashSet<>());

        return nodeRepository.save(newNode);
    }


    public Node getNodeByID(String idN){
        Optional<Node> res=nodeRepository.findById(idN);
        if(res.isPresent()){
            return res.get();
        }
        return null;
    }


    public void addChildNode(Node parent, Node child) {
        if(parent==null){
            child.setParinte(null);

            nodeRepository.saveAndFlush(child);
        }else{
            parent.addSubordonate(child);
            nodeRepository.saveAndFlush(parent);

        }

    }

    public void removeNode(String idNode) throws RuntimeException{
        Node nod=nodeRepository.findById(idNode).get();
        if(nod!=null){
            nodeRepository.delete(nod);
        }else{
            throw new RuntimeException("Nodul nu exista!");
        }

    }


    public void removeChildNode(String idParent, String idChild) throws RuntimeException{
        Node parent=nodeRepository.findById(idParent).get();
        if(parent!=null){
            Set<Node> subordinates=parent.getSubordinates();
            parent.removeSubordonate(nodeRepository.findById(idChild).get());
            nodeRepository.saveAndFlush(parent);
        }else{
            throw new RuntimeException("Parent didn't exista!");
        }

    }

    public void addCustomField(Node nod, CustomField customField){
        customField.setParentRid(nod.getId());
        nod.getCfields().add(customField);
        nodeRepository.save(nod);
       // customFieldRepository.save(customField);
    }


    public List<Node> getTree(){
        return nodeRepository.findAll();
    }

    public List<NodeDTO> getTreeDTO(){
        List<NodeDTO> rez=new ArrayList<>();
        nodeRepository.findAll().forEach(node -> {
            if(node.getParinte()!=null){
                rez.add(new NodeDTO(node.getId(),node.getLabel(),node.getDescriere(),
                        node.getParinte().getId(),null, node.getSubordinates(),node.getCfields()));
            }else{
                rez.add(new NodeDTO(node.getId(),node.getLabel(),node.getDescriere(),
                        null,node.getGeneratedID(),node.getSubordinates(),node.getCfields()));
            }
        });
        return rez;
    }

    public List<CustomField> getFieldsOfNode(String id){
        return customFieldRepository.getCustomFieldsByParentRid(id);
    }

    public List<CustomField> delField(String idN,String idF){
        CustomField cf=customFieldRepository.findById(idF).get();
        Node nod=nodeRepository.findById(idN).get();
        nod.getCfields().remove(cf);
        nodeRepository.save(nod);

        return customFieldRepository.getCustomFieldsByParentRid(idN);
    }

    public void addChildTest(String idP, Node child) {
        Node parinte=nodeRepository.findById(idP).get();
        parinte.addSubordonate(child);
        nodeRepository.saveAndFlush(parinte);
    }

    public Node getNodeEquals(String label){
        Optional<Node> n=nodeRepository.findNodeByLabel(label);

        if(n.isPresent()){
            return nodeRepository.getNodeByEquals(n.get()).get();

        }
        return null;
    }

    public Node updateNode(Node parent,Node nod) throws RuntimeException{
        nod.setParinte(parent);
       try{
           nodeRepository.saveAndFlush(nod);

       }catch (Exception e){

       }
       return nod;
    }
}
