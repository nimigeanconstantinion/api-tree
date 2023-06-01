package com.example.api_tree.model;

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
    @GeneratedValue(generator = "string_sequence")
    @GenericGenerator(
            name = "string_sequence",
            strategy = "com.example.api_tree.system.StringSequenceGenerator"
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

    @OneToMany(mappedBy = "parinte", orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Node> subordinates = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_rid")
    @Builder.Default
    private List<CustomField> cfields = new ArrayList<>();


    public void addCustomField(CustomField field){
        this.cfields.add(field);
    }


    public void addSubordonate(Node subordonate){
        subordonate.setParinte(this);
        this.subordinates.add(subordonate);
    }

}
