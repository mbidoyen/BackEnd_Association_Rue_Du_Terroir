package fr.mathieu.rueduterroir.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(schema = "association", name = "T_FILE_FLE")
@EntityListeners(AuditingEntityListener.class)
public class ImageFile {

    @Id
    @SequenceGenerator(schema = "association", name = "T_FILE_FLE_FLE_ID_SEQ", sequenceName = "T_FILE_FLE_FLE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_FILE_FLE_FLE_ID_SEQ")
    @Column(name = "fle_id")
    private Integer id;

    @Column(name = "fle_link", nullable = false)
    private String filename;

    @CreatedDate
    @Column(name = "fle_created_at", nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "fle_updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "fle_is_archived", nullable = false)
    private Boolean isArchived;

    @OneToOne
    @JoinColumn(name = "tnt_uuid", nullable = false)
    private Tenant tenant;

    @Override
    public String toString() {
        return "ImageFile{" +
                "id=" + id +
                ", fileName='" + filename + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isArchived=" + isArchived +
                '}';
    }
}
