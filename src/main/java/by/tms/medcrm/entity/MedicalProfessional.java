package by.tms.medcrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "medical_professionals")
@IdClass(MedicalProfessionalId.class)
@Getter
@Setter
@ToString
public class MedicalProfessional implements Persistable<MedicalProfessionalId> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", nullable = false)
    private MedicalProfessionalCategory category;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 32)
    private String lastName;

    @Override
    public MedicalProfessionalId getId() {
        return new MedicalProfessionalId(this.id, this.category != null ? this.category.getId() : null);
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.id == null;
    }
}
