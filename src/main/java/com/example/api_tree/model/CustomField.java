package com.example.api_tree.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CustomField")
@Table(name = "customfield")

public class CustomField {
    @Id
    @GeneratedValue(generator = "string_sequence")
    @GenericGenerator(
            name = "string_sequence",
            strategy = "com.example.api_tree.system.StringSequenceGenerator"
    )
    @Column(name = "id")
    private String id;

    String customKey;
    String value;
    String type;


    public boolean equals(Object e){
        CustomField ee=(CustomField) e;
        if(id==ee.id&&customKey.equals(ee.getCustomKey())){
            return true;
        }
        return false;
    }

    @Column(name = "parent_rid", length = 50)
    private String parentRid;


}
