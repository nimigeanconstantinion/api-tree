package com.example.api_tree.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
@Entity(name="Relatii")
@Table(name = "relatii")
public class Relatii implements Serializable {


    @Id
    @SequenceGenerator(name = "relatii_sequence",sequenceName = "relatii_sequence",allocationSize = 1)
    @GeneratedValue(generator = "relatii_sequence")
    private Long id;

    @Embedded
    @AttributeOverride(name = "idSource",column = @Column(name = "id_parinte"))
    @AttributeOverride(name = "idOwner",column = @Column(name = "parinte_id"))

    @AttributeOverride(name = "label",column = @Column(name = "label_parinte"))
    @AttributeOverride(name = "descriere",column = @Column(name = "descriere_parinte"))
    @AttributeOverride(name = "fel",column = @Column(name = "fel_parinte"))
    @AttributeOverride(name = "qnt",column = @Column(name = "qnt_parinte"))
    private SourceData source1;

    @Embedded
    @AttributeOverride(name = "idSource",column = @Column(name = "id_copil"))
    @AttributeOverride(name = "idOwner",column = @Column(name = "copil_id"))

    @AttributeOverride(name = "label",column = @Column(name = "label_copil"))
    @AttributeOverride(name = "descriere",column = @Column(name = "descriere_copil"))
    @AttributeOverride(name = "fel",column = @Column(name = "fel_copil"))
    @AttributeOverride(name = "qnt",column = @Column(name = "qnt_copil"))
    private SourceData source2;

    private String relationType;

    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @Builder.Default
    List<CustomField> customFieldList= new ArrayList<>();


}
