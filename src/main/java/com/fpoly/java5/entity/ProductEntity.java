package com.fpoly.java5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, columnDefinition = "decimal(19, 4)")
    private Integer price;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    @Column(name = "created_at", nullable = false)
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ImageEntity> images;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<CartDetailEntity> cartDetails;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetails;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<WishlistEntity> wishlists;
    
    public void setDateCreated() {
        this.dateCreated = new Date();
    }
}