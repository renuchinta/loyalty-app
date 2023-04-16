package com.loyalty.model;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    private String productName;
    private String productImage;
   /* @OneToOne(mappedBy = "product")
    @JoinColumn(name = "business_id")
    private BusinessUser businessUser;*/
    @OneToOne
    @JoinColumn(name = "product_offer_id")
    private ProductOffer productOffer;


    public void addProductOffer(ProductOffer productOffer){
        this.productOffer=productOffer;
    }


}
