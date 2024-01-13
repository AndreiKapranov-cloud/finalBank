package com.example.finalbank.repository.impl;

import com.example.finalbank.db.DB;
import com.example.finalbank.entity.Account;
import com.example.finalbank.repository.AccountDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {


    Connection connection = DB.getConnection();
    public AccountDaoImpl() throws SQLException {}

    static AccountDaoImpl aDIdas;

    static {
        try {
            aDIdas = new AccountDaoImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Account> getAllAccounts(){
        String GETALLACC = "select * from Account where is_deleted = false";

        try {
            PreparedStatement statement = connection.prepareStatement(GETALLACC);

            List<Account> result = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getInt(1));
                account.setBalance(resultSet.getInt(2));
                account.setBankId(resultSet.getInt(3));

                result.add(account);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Account> getAccountsByBank(int ofBankId) {
       final String GETBYBANK = "select * from account where is_deleted = false AND bankId = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(GETBYBANK);

            statement.setInt(1, ofBankId);
            ResultSet resultSet = statement.executeQuery();

            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getInt(1));
                account.setBalance(resultSet.getInt(2));
                account.setBankId(resultSet.getInt(3));

                accounts.add(account);
            }

            return accounts;

        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public Account getAccountById(int accountId) {
       final String GETBYID = "select * from account where is_deleted = false AND account_id = ?";
        try {
             PreparedStatement statement = connection.prepareStatement(GETBYID);

            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            Account account = new Account();
            if (resultSet.next()) {
                account.setAccountId(resultSet.getInt(1));
                account.setBalance(resultSet.getInt(2));
                account.setBankId(resultSet.getInt(3));

            }
            return account;

        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public Account saveAccount(Account account) throws SQLException {

        final String INSERT = "INSERT INTO account (balance, bank_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, account.getBalance());
            preparedStatement.setInt(2, account.getBankId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        account.setAccountId(generatedId);
                    } else {
                        throw new SQLException("Failed to get generated key.");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return account;
    }


    @Override
    public boolean removeAccount(int accountId) {
        final String REMOVE = "UPDATE account SET is_deleted = true WHERE account_id = ?";
        try {
            PreparedStatement insertStmt1 =
                    connection.prepareStatement(REMOVE);
            insertStmt1.setInt(1, accountId);
            int result = insertStmt1.executeUpdate();
            if (result > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

     }

    @Override
    public List<Account> getAccountsByTransaction(int transactionId) {
        final String GETBYTRANSACTION = "SELECT ac.account_id,ac.balance,ac.bank_id FROM account_transactions actr " +
                "INNER JOIN account ac ON ac.account_id = actr.account_id where transaction_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(GETBYTRANSACTION);

            statement.setInt(1, transactionId);
            ResultSet resultSet = statement.executeQuery();

            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getInt(1));
                account.setBalance(resultSet.getInt(2));
                account.setBankId(resultSet.getInt(3));

                accounts.add(account);
            }

            return accounts;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public synchronized void transfer(int amount,int fromAccountId,int toAccountId) throws SQLException {
   final String TRANSFER = "INSERT INTO account_transactions (account_id,transaction_id) VALUES (?,?)";
    try{
            int transactionId = withdraw(amount,fromAccountId);

            depositWithoutTransaction(amount,toAccountId);
            PreparedStatement insertStmt1 =
                    connection.prepareStatement(TRANSFER);

            insertStmt1.setInt(1,toAccountId);
            insertStmt1.setInt(2,transactionId);
            insertStmt1.executeUpdate();


    } catch (SQLException e) {
        throw new RuntimeException();
    }
        }


    @Override
    public synchronized int depositWithoutTransaction(int amount,int accountId) {
      final String DEPWITHOUTTR = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
       try{
            if(amount > 0) {
                PreparedStatement updateStmt =
                        connection.prepareStatement(DEPWITHOUTTR);
                updateStmt.setInt(1,amount);
                updateStmt.setInt(2, accountId);
                updateStmt.executeUpdate();

            }
            return accountId;
         } catch (SQLException e) {
        throw new RuntimeException();
        }
    }

    @Override
    public synchronized void deposit(int amount, int accountId) {
            final String CURRENCY = "USD";
            final String TRANSACTIONS = "INSERT INTO transactions (amount,timestemp,currency) VALUES (?, ?,?) RETURNING transaction_id";
            final String TRANSACTIONSACCOUNT = "INSERT INTO account_transactions (account_id,transaction_id) VALUES (?,?)";

            int transactionId = 0;
            depositWithoutTransaction(amount,accountId);
           try{
            PreparedStatement insertStmt =
                    connection.prepareStatement(TRANSACTIONS);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            insertStmt.setInt(1,amount);
            insertStmt.setTimestamp(2, timestamp);

            insertStmt.setString(3,CURRENCY);

            ResultSet rs = insertStmt.executeQuery();

            while (rs.next()) {

                transactionId =  rs.getInt("transaction_id");
            }

            PreparedStatement insertStmt1 =
                    connection.prepareStatement(TRANSACTIONSACCOUNT);

            insertStmt1.setInt(1,accountId);
            insertStmt1.setInt(2,transactionId);
            insertStmt1.executeUpdate();



        } catch (SQLException e) {
        throw new RuntimeException();
       }


    }

    @Override
    public int getBalance(int accountId)  {

        int balance = 0;
        try{

        PreparedStatement stmt = connection.prepareStatement("select balance from account where account_id=?");
        stmt.setInt(1, accountId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            balance =  rs.getInt("balance");
        }
        return balance;


      } catch (SQLException e) {
        throw new RuntimeException();
        }
    }

    @Override
    public synchronized int withdraw(int amount, int accountId) throws SQLException {
            final String WITHDRAW = "UPDATE account SET balance = balance - ? WHERE account_id = ?";
            final String WITHTRANS = "INSERT INTO transactions (amount,timestemp,currency) VALUES (?, ?,?) RETURNING transaction_id";
            final String WITHACTRANS = "INSERT INTO account_transactions (account_id,transaction_id) VALUES (?,?)";
            int transactionId = 0;
            int balance = aDIdas.getBalance(accountId);
            final String CURRENCY = "USD";
            if(amount > balance) {
                System.out.println("Insufficient founds");
            }else if(amount <= balance) {
                PreparedStatement updateStmt =
                        connection.prepareStatement(WITHDRAW);
                updateStmt.setInt(1,amount);
                updateStmt.setInt(2, accountId);
                updateStmt.executeUpdate();


                PreparedStatement insertStmt =
                        connection.prepareStatement(WITHTRANS);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());


                insertStmt.setInt(1,amount);
                insertStmt.setTimestamp(2, timestamp);

                insertStmt.setString(3,CURRENCY);

                ResultSet rs = insertStmt.executeQuery();
                while (rs.next()) {

                    transactionId =  rs.getInt("transaction_id");


                }

                PreparedStatement insertStmt1 =
                        connection.prepareStatement(WITHACTRANS);

                insertStmt1.setInt(1,accountId);
                insertStmt1.setInt(2,transactionId);
                insertStmt1.executeUpdate();

            }
            return transactionId;
        }
    }



