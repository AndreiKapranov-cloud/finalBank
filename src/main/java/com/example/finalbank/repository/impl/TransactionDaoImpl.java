package com.example.finalbank.repository.impl;

import com.example.finalbank.db.DB;
import com.example.finalbank.entity.Transaction;
import com.example.finalbank.repository.TransactionDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    Connection connection = DB.getConnection();

    public TransactionDaoImpl() throws SQLException {
    }

    @Override
    public List<Transaction> getAllTransactions() {
        String GETALLTR = "select * from transactions where is_deleted = false";

        try {
            PreparedStatement statement = connection.prepareStatement(GETALLTR);

            List<Transaction> result = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();

                transaction.setTransactionId(resultSet.getInt(1));
                transaction.setAmount(resultSet.getInt(2));
                transaction.setTimestamp(resultSet.getTimestamp(3));
                transaction.setCurrency(resultSet.getString(4));

                result.add(transaction);

            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        final String GETBYID = "select * from transactions where is_deleted = false AND transaction_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(GETBYID);

            statement.setInt(1, transactionId);
            ResultSet resultSet = statement.executeQuery();


            Transaction transaction = new Transaction();
            if (resultSet.next()) {
                transaction.setTransactionId(resultSet.getInt(1));
                transaction.setAmount(resultSet.getInt(2));
                transaction.setTimestamp(resultSet.getTimestamp(3));
                transaction.setCurrency(resultSet.getString(4));
                transaction.setDeleted(resultSet.getBoolean(5));


            }
            return transaction;

        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public List<Transaction> getTransactionsByAccount(int AccountId) {

        final String GETBYACCOUNT = "Select tr.transaction_id,tr.amount,tr.timestemp,tr.currency from account_transactions actr\n" +
                "INNER JOIN transactions tr ON tr.transaction_id = actr.transaction_id where account_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(GETBYACCOUNT);

            statement.setInt(1, AccountId);
            ResultSet resultSet = statement.executeQuery();

            List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(resultSet.getInt(1));
                transaction.setAmount(resultSet.getInt(2));
                transaction.setTimestamp(resultSet.getTimestamp(3));
                transaction.setCurrency(resultSet.getString(4));
                transactions.add(transaction);
            }

            return transactions;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public boolean removeTransaction(int transactionId) {
        final String REMOVE = "UPDATE transaction SET is_deleted = true WHERE transaction_id = ?";
        try {
            PreparedStatement insertStmt1 =
                    connection.prepareStatement(REMOVE);

            insertStmt1.setInt(1, transactionId);

            insertStmt1.executeUpdate();
            int result = insertStmt1.executeUpdate();
            if (result > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

}
