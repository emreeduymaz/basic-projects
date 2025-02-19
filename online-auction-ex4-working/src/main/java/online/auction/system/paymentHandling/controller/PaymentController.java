package online.auction.system.paymentHandling.controller;

import online.auction.system.paymentHandling.model.OnlinePayment;
import online.auction.system.paymentHandling.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")


   public ResponseEntity<OnlinePayment> createPayment(@RequestParam("total") Double total,
                                                       @RequestParam("currency") String currency,
                                                       @RequestParam("method") String method,
                                                       @RequestParam("intent") String intent,
                                                       @RequestParam("description") String description,
                                                       @RequestParam("cancelUrl") String cancelUrl,
                                                       @RequestParam("successUrl") String successUrl) {
        try {
            OnlinePayment payment =
                    paymentService.createPayment(total, currency, method, intent,
                            description, cancelUrl, successUrl);
            return ResponseEntity.ok(payment);
        } catch (PayPalRESTException e) {
            // Log the error message to console (or a log file in a real world application)
            System.err.println(e.getMessage());

            // It would be even better to return a meaningful error message in the response
            return ResponseEntity.badRequest().body(null);
        }}

    @PostMapping("/execute")
    public ResponseEntity<OnlinePayment> executePayment(@RequestParam("paymentId") String paymentId,
                                                        @RequestParam("payerId") String payerId) {
        try {
            OnlinePayment payment = paymentService.executePayment(paymentId, payerId);
            return ResponseEntity.ok(payment);
        } catch (PayPalRESTException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
