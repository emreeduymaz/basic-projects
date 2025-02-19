package online.auction.system.paymentHandling.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import online.auction.system.paymentHandling.model.OnlinePayment;
import online.auction.system.paymentHandling.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private APIContext apiContext;

    @Autowired
    private PaymentRepository repo;

    public OnlinePayment createPayment(Double total, String currency, String method,
                                       String intent, String description,
                                       String cancelUrl, String successUrl) throws PayPalRESTException {

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        com.paypal.api.payments.Payment createdPayment = payment.create(apiContext);

        // Storing in local DB
        OnlinePayment paymentEntity = new OnlinePayment();
        paymentEntity.setId(Long.valueOf(createdPayment.getId()));
        paymentEntity.setAmount(total);
        paymentEntity.setCurrency(currency);
        paymentEntity.setMethod(method);
        paymentEntity.setIntent(intent);
        paymentEntity.setDescription(description);
        repo.save(paymentEntity);

        return paymentEntity;
    }

    public OnlinePayment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        com.paypal.api.payments.Payment executedPayment = payment.execute(apiContext, paymentExecute);

        // Updating the payment in local DB
        OnlinePayment paymentEntity = repo.findById(Long.valueOf(paymentId)).orElse(null);
        if (paymentEntity != null) {
            paymentEntity.setTransactionId(executedPayment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId());
            repo.save(paymentEntity);
        }

        return paymentEntity;
    }
}



