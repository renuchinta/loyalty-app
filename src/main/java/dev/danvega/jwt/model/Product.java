package dev.danvega.jwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private Long id;

    private String productName;
    private String productImage;

    @OneToOne(mappedBy = "product")
    private BusinessUser businessUser;

    @OneToOne
    private ProductOffer productOffer;

}
