package transaction;

public class Deposit extends Transaction{
    public Deposit(double amount) {
        super(amount, TransactionType.DEPOSIT);
    }
}
