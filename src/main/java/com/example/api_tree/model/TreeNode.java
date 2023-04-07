package com.example.api_tree.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class TreeNode {

    private String key;
    private String label;
    private DTOSourceData data;
    private List<TreeNode> children=new ArrayList<>();
}
