package account.repository;

import account.Account;
import core.config.DBConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AccountDAOImpl implements AccountDAO{
    private final Connection conn;

    public AccountDAOImpl(DBConfig config) throws SQLException {
        this.conn = config.getConnection();
    }

    @Override
    public void create(Account object) {

    }

    @Override
    public void update(Account object) {

    }

    @Override
    public void delete(Account object) {

    }

    @Override
    public Account get(UUID id) {
        return null;
    }

    @Override
    public List<Account> getAll() {
        return null;
    }
}
