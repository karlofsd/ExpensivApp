package history.services;

import category.Category;
import history.History;
import history.HistoryFilter;
import transaction.Deposit;
import transaction.Expense;
import transaction.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HistoryService {

    public HistoryService() {
    }

    public void newDeposit(History history,double amount){
        Transaction transaction = new Deposit(amount);
        history.add(transaction);
    }

    public void newExpense(History history,double amount, Category category){
        Expense expense = new Expense(amount,category);
        history.add(expense);
    }

    public List<Transaction> getAll(History history){
        return history.getAll();
    }

    public List<Transaction> getBy(History history, HistoryFilter by){
        List<Transaction> transactions = history.getAll();
        LocalDate today = LocalDate.now();
        List<Transaction> matchTransactions = new ArrayList<>();
        switch (by){
            case BY_DAY -> {
                 matchTransactions.addAll(transactions.stream().filter(transaction -> {
                     LocalDate date = LocalDate.from(transaction.getDateTime());
                     return date.isEqual(today);
                 }).toList());
            }
            case BY_WEEK -> {
                boolean onContinue = true;
                int count = 0;
                do{
                  String day = today.getDayOfWeek().minus(count).name().toLowerCase();
                  if(day.equals("monday")) onContinue = false;
                  else count++;
                }while (onContinue);
                int finalCount = count;
                matchTransactions.addAll(transactions.stream().filter(transaction -> {
                    LocalDate date = LocalDate.from(transaction.getDateTime());
                    return date.isAfter(today.minusDays(finalCount+1)) && date.isBefore(today.plusDays(1));
                }).toList());
            }
            case BY_MONTH -> {
                matchTransactions.addAll(transactions.stream().filter(transaction -> {
                    boolean sameMonth = transaction.getDateTime().getMonth().equals(today.getMonth());
                    boolean sameYear =  transaction.getDateTime().getYear() == today.getYear();
                    return sameMonth && sameYear;
                }).toList());
            }
        }
        return matchTransactions;
    }

    public List<Transaction> get(History history, String id){
        return history.getAll().stream().filter(transaction -> transaction.getId().toString().equals(id)).toList();
    }
}
