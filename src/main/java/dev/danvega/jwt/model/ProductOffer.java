package dev.danvega.jwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOffer {
    @Id
    private Long id;

    private String offerName;
    private Integer freeQuantity;
    private Integer purchaseQuantity;


}
