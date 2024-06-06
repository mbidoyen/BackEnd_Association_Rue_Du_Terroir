package fr.mathieu.rueduterroir.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_R_ADDRESS_ADS", schema = "ASSOCIATION")
public class Address {

    @Id
    @Column(name = "ADS_ID")
    private Integer id;

    @Column(name = "ADS_ENTRY")
    private Integer entryNumber;

    @Column(name = "ADS_FLOOR")
    private Integer floorNumber;

    @Column(name = "ADS_APARTMENT")
    private Integer apartmentNumber;

    @OneToOne(mappedBy = "address")
    private Tenant tenant;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", entryNumber=" + entryNumber +
                ", floorNumber=" + floorNumber +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }
}
