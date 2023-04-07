package com.example.api_tree.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="CustomField")
@Table(name="custom_field")
public class CustomField{
    @Id
    @SequenceGenerator(name = "cfields_sequence",sequenceName = "cfields_sequence",allocationSize = 1)
    @GeneratedValue(generator = "cfields_sequence")
    private Long id;

    Long idOwner;
    String customKey;
    String value;
    String type;

    @Column(name = "parent_id")
    private Long parentId;



}
