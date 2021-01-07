package com.example.project.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String name;
    private double price;
    private int amount;
    private int unitIndex;
    private String comment;
    private int categoryIndex;
}
