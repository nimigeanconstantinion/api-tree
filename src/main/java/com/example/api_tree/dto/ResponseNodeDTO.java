package com.example.api_tree.dto;

import com.example.api_tree.model.CustomField;
import com.example.api_tree.model.Node;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class ResponseNodeDTO {
    private String id;
    private String label;
    private List<CustomField> fields;
    private List<Node> subordinates;

    public ResponseNodeDTO(String id,String label){
        this.label=label;
        this.id=id;
    }


}
