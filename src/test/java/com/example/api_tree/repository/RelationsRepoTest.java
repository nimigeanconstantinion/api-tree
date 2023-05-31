package com.example.api_tree.repository;

import com.example.api_tree.ApiTreeApplication;
import com.example.api_tree.model.SourceData;
import com.example.api_tree.services.RelationsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiTreeApplication.class)
class RelationsRepoTest {

    @Autowired
    RelationsRepo relationsRepo;
    @Autowired
    RelationsService relationsService;
    @Test
    void getAllChildrenByIDParinteTest() {

    }

    @Test
    void findAllBySource2() {
        SourceData s=new SourceData();
        s.setLabel("Label01 de copil");
        SourceData s2=new SourceData();
        s2.setLabel("Label01 de copil");
        System.out.println(s.equals(s2));
        System.out.println(relationsRepo.findAllBySource2(s.getLabel()).size());

    }
}