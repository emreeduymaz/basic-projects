package online.auction.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name ="bids")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="amount",nullable = false)
    private Double amount;

    @Column(name ="bid_time")
    private LocalDateTime bidTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auctions_id", nullable = false)
    private Auction auction;

}
