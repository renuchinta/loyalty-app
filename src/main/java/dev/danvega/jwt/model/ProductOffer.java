package dev.danvega.jwt.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
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
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class ProductOffer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    private String offerName;
    private Integer freeQuantity;
    private Integer purchaseQuantity;
    private String productOfferImageUrl;


    @OneToOne(mappedBy = "productOffer")
    private Product product;

    public void addProduct(Product product){
        this.product=product;
    }
}
