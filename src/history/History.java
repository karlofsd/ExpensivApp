package history;

import transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class History{
    private final List<Transaction> transactions = new ArrayList<>();

    public History() {
    }

    public void add(Transaction transaction) {
        this.transactions.add(transaction);
    }

    void remove(Transaction transaction) {
        this.transactions.remove(transaction);
    }

//    public Transaction get(String id) {
//
//        return this.transactions.stream().filter(transaction -> transaction.getId().toString().equals(id)).findFirst().get();
//    }

    public List<Transaction> getAll() {
        return this.transactions;
    }
}
