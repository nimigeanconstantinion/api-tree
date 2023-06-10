package com.example.api_tree.services;

import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Node;
import com.example.api_tree.repository.CustomFieldRepository;
import com.example.api_tree.repository.NodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class NodeServices {


    private NodeRepository nodeRepository;
    private CustomFieldRepository customFieldRepository;

    public Node createNode(String label, Node parent) {
        Node newNode = new Node();
        newNode.setLabel(label);
        newNode.setParinte(parent);
        newNode.setCfields(new ArrayList<>());

        return nodeRepository.save(newNode);
    }

    public void addChildNode(Node parent, Node child) {
        parent.getSubordinates().add(child);
        child.setParinte(parent);
        nodeRepository.saveAndFlush(parent);
    }

    public void removeChildNode(String idParent, String idChild) throws RuntimeException{
        Node parent=nodeRepository.findById(idParent).get();
        if(parent!=null){
            List<Node> subordinates=parent.getSubordinates();
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
}
