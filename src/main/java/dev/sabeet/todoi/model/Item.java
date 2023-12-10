package dev.sabeet.todoi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Item")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String owner;
    private String description;
}
