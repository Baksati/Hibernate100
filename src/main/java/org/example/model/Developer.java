package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE', 'DELETED') DEFAULT 'ACTIVE'")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "developers_skills",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();

    @ManyToOne
    @JoinTable(
            name = "developers_specialties",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Specialty specialty;

    @Override
    public String toString() {
        return "Developer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", status=" + status + ", specialty=" + specialty + ", skills=" + skills + "]";
    }
}

