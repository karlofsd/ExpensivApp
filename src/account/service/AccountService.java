package account.service;

import account.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final List<Account> accounts = new ArrayList<>();

    public AccountService() {
    }

    public List<Account> getAll() {
        return this.accounts;
    }

    public void create(Account account){
        this.accounts.add(account);
    }

    public void delete(Account account){
        this.accounts.remove(account);
    }
}
