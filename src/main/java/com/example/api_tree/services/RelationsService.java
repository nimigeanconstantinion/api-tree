package com.example.api_tree.services;

import com.example.api_tree.model.*;
import com.example.api_tree.repository.CustomFieldsRepo;
import com.example.api_tree.repository.RelationsRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelationsService {
    private RelationsRepo relationsRepo;
    private CustomFieldsRepo customFieldsRepo;

    public RelationsService(RelationsRepo relationsRepo, CustomFieldsRepo customFieldsRepo) {
        this.relationsRepo = relationsRepo;
        this.customFieldsRepo = customFieldsRepo;
    }

    public List<DTOSourceData> getAllChildrenOf(Long id){
        return relationsRepo.getAllChildrenByIDParinte(id)
                .stream()
                .map(t->{
                    return toDTO(t);
                }).collect(Collectors.toList());
    }

    public List<DTOSourceData> getAllParents() {
        List<Relatii> relatiiList = relationsRepo.findAll();
        return relatiiList.stream().filter((a) -> {
            Long b = a.getSource1().getIdSource();
            return relatiiList.stream().map(c -> c.getSource2().getIdSource())
                    .filter(d -> d == b).collect(Collectors.toList()).size() == 0;
        }).map(b -> {
            DTOSourceData dt = new DTOSourceData();
            dt.setLabel(b.getSource1().getLabel());
            dt.setFel(b.getSource1().getFel());
            dt.setDescriere(b.getSource1().getDescriere());
            dt.setId_superior(null);
            dt.setIdSource(b.getSource1().getIdSource());
            dt.setCustomFieldList(customFieldsRepo.getCustomFieldById(b.getSource1().getIdSource()));
            return dt;
        }).collect(Collectors.toList());
    }

    public TreeNode toNode(DTOSourceData data, int level) {
        TreeNode tmp = new TreeNode();
        tmp.setLabel(data.getLabel());
        tmp.setChildren(new ArrayList<>());
        tmp.setData(data);
        String k = "";
        for (int i = 0; i <= level; i++) {
            k = k + String.valueOf(level) + "-";
        }

        tmp.setKey(k.substring(0, k.length() ));
        return tmp;
    }

    public DTOSourceData toDTO(SourceData s) {
        DTOSourceData dt = new DTOSourceData(s,new ArrayList<>());
//
//
        long idd=relationsRepo.getParinteByID(s.getIdSource()).get().getIdSource();

        dt.setId_superior(idd);
        dt.setQnt(0);
        dt.setCustomFieldList(relationsRepo.getCustomFieldyID(s.getIdSource()));
        return dt;
    }

    public TreeNode getNodgetOrigin(long id){
        SourceData data=relationsRepo.getParinteByID(id).get();
        DTOSourceData dtoData=toDTO(data);
        return toNode(dtoData,0);


    }

    public List<TreeNode> getTree(TreeNode origin) {
        List<TreeNode> allLev = new ArrayList<>();
        List<TreeNode> level = new ArrayList<>();
        allLev.add(origin);
        level.add(origin);
        int lev = 0;
        boolean sw = true;
        while (sw==true) {
            final int lv = lev + 1;
            List<TreeNode> tmp = new ArrayList<>();
            for (TreeNode n : level
            ) {
                tmp = relationsRepo.getAllChildrenByIDParinte(n.getData().getIdSource())
                        .stream()
                        .map(t -> {
                            return toNode(toDTO(t), lv);

                        }).collect(Collectors.toList());
                n.setChildren(tmp);

            }
            if (tmp.size() == 0) {
                sw = false;
            } else {

                level = tmp;
            }

        }

      return allLev;
    }



}