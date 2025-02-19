package online.auction.system.paymentHandling.repository;

import online.auction.system.paymentHandling.model.OnlinePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<OnlinePayment,Long> {
}
