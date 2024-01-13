package com.example.finalbank.repository.impl;

import com.example.finalbank.db.DB;
import com.example.finalbank.entity.Bank;
import com.example.finalbank.repository.BankDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankDaoImpl implements BankDao {
        Connection connection = DB.getConnection();

        public BankDaoImpl() throws SQLException {
        }
       static BankDaoImpl  bDI;

        static {
        try {
            bDI = new BankDaoImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
        @Override
        public  List<Bank> getAllBanks(){
         String GETALLBANKS = "select * from Bank where is_deleted = false";

        try {
        PreparedStatement statement = connection.prepareStatement(GETALLBANKS);

        List<Bank> result = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Bank bank = new Bank();
         //   bank.setName(resultSet.getString(1));
            bank.setBankId(resultSet.getInt(1));
            bank.setName(resultSet.getString(2));
            result.add(bank);
        }
        return result;
       } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException();
      }
  }




    @Override
    public Bank saveBank(Bank bank) {

        final String INSERT = "INSERT INTO bank (name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bank.getName());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        bank.setBankId(generatedId);
                    } else {
                        throw new SQLException("Failed to get generated key.");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        }
        return bank;
          } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        @Override
        public Bank getBankByName(String name) {

            final String GETBYNAME = "select * from bank where is_deleted = false AND bankName = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(GETBYNAME);

                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();

                Bank bank = new Bank();
                if (resultSet.next()) {
                    bank.setName(resultSet.getString(1));
                    bank.setBankId(resultSet.getInt(2));

                }
                return bank;

            } catch (SQLException e) {
                throw new RuntimeException();
            }

        }

        @Override
        public Bank getBankByAccount(int AccountId) {

            final String GETBYACCOUNT = "SELECT b.bank_id,b.name,b.is_deleted FROM bank b left join account ac ON ac.bank_id = b.bank_id where ac.account_id = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(GETBYACCOUNT);

                statement.setInt(1, AccountId);
                ResultSet resultSet = statement.executeQuery();

                Bank bank = new Bank();
                if (resultSet.next()) {
                    bank.setBankId(resultSet.getInt(1));
                    bank.setName(resultSet.getString(2));
                    bank.setDeleted(resultSet.getBoolean(3));
                }
                return bank;

            } catch (SQLException e) {
                throw new RuntimeException();
            }


        }

        @Override
        public boolean updateBank(Bank updatedBank) {
            final String UPDATEBANK = "UPDATE bank SET name = ? WHERE bank_id = ?";

            try {
                PreparedStatement ps = connection.prepareStatement(UPDATEBANK);
                ps.setObject(1, updatedBank.getName());
                ps.setObject(2, updatedBank.getBankId());

                int affectedRows = ps.executeUpdate();
                ps.close();

                return affectedRows > 0;

            } catch (SQLException e) {

            }
            return false;
        }


        public boolean removeBank(int bankId) {
            final String REMOVE = "UPDATE bank SET is_deleted = true WHERE bank_id = ?";
            try {
                PreparedStatement insertStmt1 =
                        connection.prepareStatement(REMOVE);

                insertStmt1.setInt(1, bankId);

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
    public Bank getBankById(int bankId) {
        final String GETBYID = "select * from bank where is_deleted = false AND bank_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(GETBYID);

            statement.setInt(1, bankId);
            ResultSet resultSet = statement.executeQuery();

            Bank bank = new Bank();
            if (resultSet.next()) {

                bank.setBankId(resultSet.getInt(1));
                bank.setName(resultSet.getString(2));

            }
            return bank;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}


