package fr.mathieu.rueduterroir.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(schema = "ASSOCIATION", name = "T_TENANT_TNT")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "TNT_UUID")
    private UUID uuid;

    @Column(name = "TNT_CIVILITY", nullable = false)
    private String civility;

    @Column(name = "TNT_FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "TNT_LASTNAME", nullable = false)
    private String lastName;

    @Column(name = "TNT_EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "TNT_PASSWORD", nullable = false)
    private String password;

    @Column(name = "TNT_OCCUPANTS", nullable = false)
    private Integer occupants;

    @Column(name = "TNT_PHONE", nullable = false)
    private String phone;

    @Column(name = "TNT_GARAGE")
    private Integer garage;

    @Column(name = "TNT_NBR_TENANT", nullable = false, unique = true)
    private String tenantNumber;

    @CreatedDate
    @Column(name = "TNT_CREATED_AT", nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "TNT_UPDATED_AT", nullable = false)
    private Date updatedAt;

    @Column(name = "TNT_IS_ARCHIVED", nullable = false)
    private Boolean isArchived = false;

    @Column(name = "TNT_IS_ENABLED", nullable = false)
    private Boolean isEnabled;

    @OneToOne
    @JoinColumn(name = "ADS_ID", unique = true, nullable = false)
    private Address address;

    @OneToOne(mappedBy = "tenant")
    private ImageFile imageFile;

    @ManyToMany
    @JoinTable(name = "T_ROLE_RLE_USR", schema = "AUTHENTICATION",
            joinColumns = @JoinColumn(name = "TNT_UUID"),
            inverseJoinColumns = @JoinColumn(name = "RLE_ID")
    )
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getTenants().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getTenants().remove(this);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "uuid=" + uuid +
                ", civility='" + civility + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", occupants=" + occupants +
                ", phone='" + phone + '\'' +
                ", garage=" + garage +
                ", tenantNumber='" + tenantNumber + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isArchived=" + isArchived +
                ", isEnabled=" + isEnabled +
                ", address=" + address +
                ", roles=" + roles +
                '}';
    }
}
