package com.example.api_tree.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Embeddable
public class SourceData implements Serializable {

    private Long idSource;

    private String label;

    private String descriere;

    private String fel;

    private double qnt;

    public boolean equals(Object o){
        SourceData sourceData=(SourceData) o;
        return this.getLabel().equals(sourceData.getLabel());
    }

}
