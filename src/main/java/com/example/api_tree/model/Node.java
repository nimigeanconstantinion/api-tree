package com.example.api_tree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity(name = "Node")
@Table(name="node")
public class Node {
    @Id
//    @SequenceGenerator(
//            name="pers_sequence",
//            sequenceName = "pers_sequence",
//            allocationSize = 1
//
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "pers_sequence"
//    )

    @GenericGenerator(
            name = "string_sequence",
            strategy = "com.example.api_tree.system.StringSequenceGenerator"
    )
    @GeneratedValue(generator = "string_sequence"
    )

    @Column(name = "id")
    private String id;
    private String label;
    private String descriere;

    public Node() {
        this.subordinates = new ArrayList<>();
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parinte_id")
    private Node parinte;

    @OneToMany(mappedBy = "id", orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Node> subordinates = new ArrayList<>();


    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_rid")
    @Builder.Default
    private List<CustomField> cfields = new ArrayList<>();


    public void addCustomField(CustomField field){
        field.setParentRid(this.id);
        this.cfields.add(field);
    }


    public void addSubordonate(Node subordonate){
        subordonate.setParinte(this);
        this.subordinates.add(subordonate);
    }

    public void removeSubordonate(Node subordonate){

        this.subordinates.remove(subordonate);
    }

}
