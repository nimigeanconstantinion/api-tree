package com.example.api_tree.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "Masina")
@Table(name = "masina")
public class Masina {
    @Id
    @SequenceGenerator(name = "c_sequence",sequenceName = "c_sequence",allocationSize = 1)
    @GeneratedValue(generator = "c_sequence")
    private Long id;

    private String label;

    private String descriere;

    private String fel;

    private double qnt;


    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @Builder.Default
    List<CustomField> customFieldList= new ArrayList<>();
}
