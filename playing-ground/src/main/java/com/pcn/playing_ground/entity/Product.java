package com.pcn.playing_ground.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product extends BaseEntity{
    @Column(name = "PRODUCT_NAME", length = 30, nullable = false)
    private String productName;
    @Column(name = "UNIT", length = 20)
    private String unit;
    @Column(name = "PRICE", precision = 10, scale = 2)
    private BigDecimal price;
    private Integer instock;
    @Column(name = "VALIDFLAG", length = 1)
    private Character validFlag;

    @ManyToOne
    @JoinColumn(name = "CategoryID", referencedColumnName = "ID", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}
