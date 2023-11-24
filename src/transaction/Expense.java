package transaction;

import category.Category;

public class Expense extends Transaction {
    private final Category category;
    private String description;

    public Expense(double amount, Category category, String description) {
        super(amount,TransactionType.EXPENSE);
        this.category = category;
        this.description = description;
    }

    public Expense(double amount, Category category) {
        super(amount,TransactionType.EXPENSE);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
