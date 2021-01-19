package com.example.project.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String name;
    private Double price;
    private Integer amount;
    private Integer unitIndex;
    private String comment;
    private Integer categoryIndex;
}
