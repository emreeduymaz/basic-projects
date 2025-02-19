import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private String transType;
    private double amount;
    private Date date;
    private Integer fromClientId;
    private Integer toClientId;

    public Transaction(String transType, double amount, Date date, Integer fromClientId, Integer toClientId) {
        this.transType = transType;
        this.amount = amount;
        this.date = date;
        this.fromClientId = fromClientId;
        this.toClientId = toClientId;
    }

    // Getters
    public String getTransType() {
        return transType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public Integer getFromClientId() {
        return fromClientId;
    }

    public Integer getToClientId() {
        return toClientId;
    }
}
