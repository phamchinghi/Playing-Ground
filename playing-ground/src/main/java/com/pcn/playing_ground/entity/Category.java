package com.pcn.playing_ground.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CATEGORIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{
    @Column(name = "CATEGORY_NAME", length = 50)
    private String categoryName;
    @Column(name = "DESCRIPTIONS", length = 255)
    private String descriptions;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
