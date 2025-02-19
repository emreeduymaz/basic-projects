package online.auction.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="name",nullable = false)
    private String name;
    @Column(name ="description",nullable = false)
    private String description;
    @Column(name ="start_price",nullable = false)
    private Double start_price;
    @ManyToOne
    @JoinColumn(name="users_id", nullable = false)
    private Users user;

}
