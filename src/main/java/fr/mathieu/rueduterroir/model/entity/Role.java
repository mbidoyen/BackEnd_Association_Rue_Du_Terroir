package fr.mathieu.rueduterroir.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "T_R_ROLE_RLE", schema = "AUTHENTICATION")
public class Role {

    @Id
    @SequenceGenerator(name = "T_R_ROLE_RLE_ID_SEQ", sequenceName = "T_R_ROLE_RLE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_R_ROLE_RLE_ID_SEQ")
    @Column(name = "RLE_ID")
    private Integer id;

    @Column(name = "RLE_NAME", nullable = false)
    private String name;

    @CreatedDate
    @Column(name = "RLE_CREATED_AT", nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "RLE_UPDATED_AT", nullable = false)
    private Date updatedAt;

    @ManyToMany(mappedBy = "roles")
    private List<Tenant> tenants = new ArrayList<>();
}
