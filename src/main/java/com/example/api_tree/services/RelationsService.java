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
       // for (int i = 0; i <= level; i++) {
            k = String.valueOf(level) + "-"+String.valueOf(data.getIdSource());
        //}

        tmp.setKey(k);
        if(level==0){
            tmp.setIcon("pi pi-fw pi-circle-fill");

        }
        if(level==1){
            tmp.setIcon("pi pi-fw pi-play");

        }
        if(level==2){
            tmp.setIcon("pi pi-fw pi-arrows-h");

        }

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

    public SourceData getSourceChildById(long id){
        Optional<SourceData> sp=relationsRepo.getChildByID(id);
        if(sp.isPresent()){
            return sp.get();
        }
        return null;
    }

    public TreeNode getTreeOrigin(long idO){
        List<SourceData> data=relationsRepo.getOrigin(idO);
        if(data.size()>0){
            DTOSourceData dtoSourceData=toDTO(data.get(0));
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
        int lv=0;
        boolean sw = true;
        while (sw==true) {
            ++lv;
            List<TreeNode> tmp = new ArrayList<>();


            int finalLv = lv;
            level.forEach(n->{
               List<DTOSourceData> nChildren=new ArrayList<>();
               nChildren=getAllChildrenOf(n.getData().getIdSource());
               nChildren.forEach(c->{
                       TreeNode nod=toNode(c, finalLv);
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

    public void sincronizeFields(long ownerId,List<CustomField> campuri){
        List<CustomField> original=customFieldsRepo.getFieldsByIdOwner(ownerId);

        if(original.size()>0){
            customFieldsRepo.deleteAll(original.stream().filter(f->{
                return campuri.stream().filter(p->p.getId()==f.getId()).count()==0;
            }).collect(Collectors.toList()));
        }
        System.out.println("Here");
        System.out.println(campuri);

        customFieldsRepo.saveAll(campuri);
    }

    public DTOSourceData addRelation(long idParinte,SourceData copil,List<CustomField> campuri){
        Optional<SourceData> objCopil=relationsRepo.getSourceChild(copil.getLabel());
        List<SourceData> objParinte=relationsRepo.getSourceParentByID(idParinte);

        if(objCopil.isEmpty()&&objParinte.size()>0){
            Relatii newR=new Relatii();
            newR.setSource2(copil);
            newR.setSource1(objParinte.get(0));
            newR.setCustomFieldList(new ArrayList<>());

            relationsRepo.save(newR);
            Optional<SourceData> savedS=relationsRepo.getSourceChild(copil.getLabel());
            if(savedS.isPresent()){
                copil.setIdSource(savedS.get().getIdSource());
                sincronizeFields(savedS.get().getIdSource(),campuri);
                return toDTO(copil);
            }
            return null;
        }else{
            return null;
        }
    //return null;
    }



}