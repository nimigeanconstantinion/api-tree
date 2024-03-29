package com.example.api_tree.model;

import com.example.api_tree.generator.models.Generator;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@Entity(name = "Node")
@Table(name="node")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Node implements Serializable {
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
        this.subordinates = new HashSet<>();
    }

    public Node(String label){
        this.label=label;

    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "parinte_id")
    @JsonBackReference
    private Node parinte;

    @JsonManagedReference
    @OneToMany(mappedBy = "parinte", orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Node> subordinates = new HashSet<>();


    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_rid")
    @Builder.Default
    private Set<CustomField> cfields = new HashSet<>();

    @OneToOne
    @JoinColumn(name="generator_id")
    private Generator generatedID;

    @Override
    public boolean equals(Object obj){
        if(this.label.equals(((Node) obj).getLabel())){
            return true;
        }
        return false;
    }

    public void addCustomField(CustomField field){
        field.setParentRid(this.id);
        this.cfields.add(field);
    }


    public void addSubordonate(Node subordonate){
        subordonate.setParinte(this);
        this.subordinates.add(subordonate);
    }

    public void addGeneratorIdentity(Generator generator){
        this.setGeneratedID(generator);
    }

    public void removeSubordonate(Node subordonate){

        this.subordinates.remove(subordonate);
    }

}
