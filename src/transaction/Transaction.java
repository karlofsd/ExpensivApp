package transaction;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public abstract class Transaction {
    private final UUID id;
    private final double amount;
    private final LocalDateTime dateTime;
    private final TransactionType type;

    public Transaction(double amount, TransactionType type) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Transaction other) {
            return this.id.equals(other.id);
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("ID: %s Tipo: %s Monto: $%s Fecha: %s",this.id,this.type,this.amount,this.dateTime.toLocalDate());
    }
}
