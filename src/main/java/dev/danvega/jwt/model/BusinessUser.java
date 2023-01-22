package dev.danvega.jwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BUSINESS_USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessUser {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private String address;
    private String userName;
    private String phoneNumber;
    private String email;

    @Override
    public String toString() {
        return "BusinessUser{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    private String password;

}
