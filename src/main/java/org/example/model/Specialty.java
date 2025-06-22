package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "specialties")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE', 'DELETED') DEFAULT 'ACTIVE'")
    private Status status;

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"status\": \"" + status + "\"\n" +
                "}";
    }
}