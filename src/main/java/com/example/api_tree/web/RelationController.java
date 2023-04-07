package com.example.api_tree.web;


import com.example.api_tree.model.*;
import com.example.api_tree.repository.RelationsRepo;
import com.example.api_tree.services.RelationsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/tree")
@CrossOrigin

public class RelationController {

    private RelationsRepo relationsRepo;
    private RelationsService relationsService;
    public RelationController(RelationsRepo relationsRepo, RelationsService relationsService){
        this.relationsRepo=relationsRepo;
        this.relationsService = relationsService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<Relatii> getAll(){
        return relationsRepo.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getChildren/{idP}")
    public List<TreeNode> getChildren(@PathVariable long idP){
        List<TreeNode> lista=relationsService.getAllChildrenOf(idP)
                .stream()
                .map(t->{
                    return relationsService.toNode(t,1);
                }).collect(Collectors.toList());

        System.out.println("______Lista de marime "+lista.size());
       return lista;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tree")
    public List<TreeNode> getTree() {

        SourceData root=new SourceData();
        root.setIdSource(0L);
        root.setLabel("ROOT");
        root.setQnt(0);
        root.setFel("R");
        root.setDescriere("Seful");

        DTOSourceData dtoorigin=relationsService.toDTO(root);
        TreeNode tnode=relationsService.toNode(dtoorigin,0);
        return relationsService.getTree(tnode);
    }



    }
