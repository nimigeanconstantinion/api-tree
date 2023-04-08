package com.example.api_tree.model;

import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder

public class DTOSourceData extends SourceData{


    private Long id_superior;

    private List<CustomField> customFieldList=new ArrayList<>();


    public DTOSourceData(SourceData source, List<CustomField> customFieldList) {
        super();
        this.setIdSource(source.getIdSource());
        this.setDescriere(source.getDescriere());
        this.setFel(source.getFel());
        this.setLabel(source.getLabel());
        this.setQnt(source.getQnt());
        this.customFieldList=customFieldList;
    }
    public String toString(){
        return "Id superior="+this.getId_superior().toString()+"\n"+"Id Source="+this.getIdSource().toString();
    }
}
