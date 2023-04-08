package com.example.api_tree.web;


import com.example.api_tree.model.*;
import com.example.api_tree.repository.RelationsRepo;
import com.example.api_tree.services.RelationsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

       return lista;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tree")
    public List<TreeNode> getTree() {

        TreeNode root=relationsService.getTreeOrigin(0L);

        return relationsService.getTree(root);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/test")
    public List<SourceData> getTest() {

        return relationsRepo.getAllChildrenByIDParinte(2L);
    }

    }
