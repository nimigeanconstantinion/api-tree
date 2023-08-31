package com.example.api_tree.dto;

import com.example.api_tree.generator.models.Generator;
import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Node;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NodeDTO {
    private String id;
    private String label;
    private String descriere;
    private String parinteID;
    private Generator generator;
    private Set<Node> subs=new HashSet<>();
   private Set<CustomField> fields=new HashSet<>();

   public NodeDTO(String id,String label){
       this.id=id;
       this.label=label;
   }

}
