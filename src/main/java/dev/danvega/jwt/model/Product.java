package dev.danvega.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    private String productName;
    private String productImage;


    @OneToOne(mappedBy = "product")
    private BusinessUser businessUser;

    @OneToOne
    @JsonIgnore
    private ProductOffer productOffer;

    public void addBusinessUser(BusinessUser businessUser){
        this.businessUser = businessUser;
    }

    public void addProductOffer(ProductOffer productOffer){
        this.productOffer=productOffer;
    }

}
