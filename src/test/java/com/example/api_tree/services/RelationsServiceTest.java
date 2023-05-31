package com.example.api_tree.services;

import com.example.api_tree.ApiTreeApplication;
import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Relatii;
import com.example.api_tree.model.SourceData;
import com.example.api_tree.repository.CustomFieldsRepo;
import com.example.api_tree.repository.RelationsRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ApiTreeApplication.class)

class RelationsServiceTest {
    @Autowired
    private RelationsRepo relationsRepo;
    private RelationsService relationsService;
    private CustomFieldsRepo customFieldsRepo;


    @Test
    void getAllChildrenOf() {

        System.out.println(relationsRepo.getChildByID(0L));

    }

    @Test
    void addRelation() {
        SourceData s=new SourceData();
        s.setLabel("New Label");
        s.setQnt(100.00);
        s.setDescriere("dekdldlkf lkflkl");
        s.setFel("C");
        s.setIdSource(11L);
        Optional<SourceData> objCopil=relationsRepo.getSourceChild(s.getLabel());
        List<SourceData> objParinte=relationsRepo.getSourceParentByID(0L);
       System.out.println(objCopil.isPresent());
        System.out.println(objParinte.get(0));
        Relatii newR=new Relatii();
        System.out.println("--------------------");
        newR.setSource2(s);
        newR.setSource1(objParinte.get(0));
        newR.setCustomFieldList(new ArrayList<>());
        newR.setRelationType("parinte-copil");
        CustomField fld1=new CustomField();
        fld1.setIdOwner(11L);
        fld1.setType("string");
        fld1.setIdOwner(11L);
        fld1.setValue("lkalaslkalsjdl lkaslalk");
        fld1.setCustomKey("camp_x");
        List<CustomField> campuri=new ArrayList<>();

        relationsRepo.save(newR);
//        campuri.add(fld1);
//        List<CustomField> original=customFieldsRepo.getFieldsByIdOwner(3L);
//        System.out.println("Lista campuri="+original.size());
        //       relationsService.sincronizeFields(11L,campuri);
 //       System.out.println(objCopil.get());

    }

}