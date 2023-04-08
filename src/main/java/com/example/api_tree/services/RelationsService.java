package com.example.api_tree.services;

import com.example.api_tree.model.*;
import com.example.api_tree.repository.CustomFieldsRepo;
import com.example.api_tree.repository.RelationsRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RelationsService {
    private RelationsRepo relationsRepo;
    private CustomFieldsRepo customFieldsRepo;

    public RelationsService(RelationsRepo relationsRepo, CustomFieldsRepo customFieldsRepo) {
        this.relationsRepo = relationsRepo;
        this.customFieldsRepo = customFieldsRepo;
    }

    public List<DTOSourceData> getAllChildrenOf(Long idP){
        return relationsRepo.getAllChildrenByIDParinte(idP)
                .stream()
                .map(t->
                     toDTO(t)
                ).collect(Collectors.toList());
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

        tmp.setKey(k.substring(0, k.length()-1));
        return tmp;
    }

    public DTOSourceData toDTO(SourceData s) {
        DTOSourceData dt = new DTOSourceData(s,new ArrayList<>());

        Optional<SourceData> parinte=relationsRepo.getParinteByID(s.getIdSource());
        long idParent=0L;
        if(parinte.isPresent()){
            idParent=relationsRepo.getParinteByID(s.getIdSource()).get().getIdSource();
        }

        dt.setId_superior(idParent);
       // dt.setQnt(0);
        dt.setCustomFieldList(relationsRepo.getCustomFieldByID(s.getIdSource()));
        return dt;
    }

    public TreeNode getTreeOrigin(long idO){
        Optional<SourceData> data=relationsRepo.getOrigin(idO);
        if(data.isPresent()){
            DTOSourceData dtoSourceData=toDTO(data.get());
            return toNode(dtoSourceData,0);

        }
        return null;


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

//            level.stream().map(n->{
//               List<DTOSourceData> nChildren=new ArrayList<>();
//               nChildren=getAllChildrenOf(n.getData().getIdSource());
//                if(nChildren.size()>0){
//                    nChildren.stream().map(v->{
//                       TreeNode nod=toNode(v,lv);
//                       n.getChildren().add(nod);
//                       tmp.add(nod);
//                       return v;
//                    });
//                }
//                return tmp;
//            });
            level.forEach(n->{
               List<DTOSourceData> nChildren=new ArrayList<>();
               nChildren=getAllChildrenOf(n.getData().getIdSource());
               nChildren.forEach(c->{
                       TreeNode nod=toNode(c,lv);
                       n.getChildren().add(nod);
                       tmp.add(nod);
               });
            });
//            for (TreeNode n : level
//            ) {
//               List<DTOSourceData> nChildren=new ArrayList<>();
//               nChildren=getAllChildrenOf(n.getData().getIdSource());
//               if(nChildren.size()>0) {
//                   for(int z=0;z<nChildren.size();z++){
//                       TreeNode nod=toNode(nChildren.get(z),lv);
//                       n.getChildren().add(nod);
//                       tmp.add(nod);
//                   }
//
//
////                   nChildren.forEach((dtn)->{
////                       TreeNode nod=toNode(dtn,lv);
////                       n.getChildren().add(nod);
////                       tmp.add(nod);
////                   });
//               }
//
//
//            }

            if (tmp.size() == 0||tmp==null) {
                sw = false;
            } else {

                level = tmp;
            }

        }

      return allLev;
    }



}