package online.auction.system.repository;

import online.auction.system.model.Bids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BidsRepository extends JpaRepository<Bids,Long> {
}
