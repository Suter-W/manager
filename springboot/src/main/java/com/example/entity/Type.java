package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String name;

    private String description;

    private String img;
}
