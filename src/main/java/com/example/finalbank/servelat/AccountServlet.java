package com.example.finalbank.servelat;

import com.example.finalbank.db.DB;
import com.example.finalbank.dto.AccountDto;
import com.example.finalbank.repository.AccountDao;
import com.example.finalbank.repository.impl.AccountDaoImpl;
import com.example.finalbank.service.AccountService;
import com.example.finalbank.service.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;


@WebServlet(urlPatterns = "/account", name = "AccountServlet")
public class AccountServlet extends HttpServlet {
    public AccountServlet() throws SQLException {
    }
    Connection connection = DB.getConnection();
    AccountDao accountDao = new AccountDaoImpl();
    AccountService service = new AccountServiceImpl(accountDao);

    ObjectMapper mapper = new ObjectMapper();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stringId = request.getParameter("accountId");
        response.setContentType("application/json");

        if (stringId == null) {
            List<AccountDto> allAccounts = service.getAllAccounts();
            String json = mapper.writeValueAsString(allAccounts);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json);
        } else {

            int accountId = Integer.parseInt(stringId);
            AccountDto accountDto = service.getAccountById(accountId);
            int checkId = accountDto.getAccountId();
            if (checkId == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested account not found.");
            } else {
                String accountJson = mapper.writeValueAsString(accountDto);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(accountJson);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AccountDto accountDto;
        try {
            accountDto = mapper.readValue(
                    req.getReader().lines().collect(Collectors.joining()), AccountDto.class);

        } catch (JsonProcessingException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request body.");
            return;
        }
        try {
            resp.getWriter().write(mapper.writeValueAsString(service.saveAccount(accountDto)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().flush();
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringId = request.getParameter("accountId");
        response.setContentType("application/json");
        try {
            int accountId = Integer.parseInt(stringId);
            AccountDto accountDto = service.getAccountById(accountId);
            int checkId = accountDto.getAccountId();
            if (checkId == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested account not found.");

            } else {

                boolean removed = service.removeAccount(accountId);

            //    response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Successfully removed: " + removed);
            }
        } catch (NumberFormatException e) {
            response.setStatus(SC_BAD_REQUEST);
            response.getWriter().write("Parameter accountId should contain only numbers.");
        }
      }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     //transfer
        String amount = request.getParameter("amount");
        String fromAccountId = request.getParameter("fromAccountId");
        String toAccountId = request.getParameter("toAccountId");

            try {
            int amountInt = Integer.parseInt(amount);
            int fromAccountIdInt = Integer.parseInt(fromAccountId);
            int toAccountIdInt = Integer.parseInt(toAccountId);

                AccountDto fromAccountDto = service.getAccountById(fromAccountIdInt);
                AccountDto toAccountDto = service.getAccountById(toAccountIdInt);

                int checkFromId = fromAccountDto.getAccountId();
                int checkToId = toAccountDto.getAccountId();

                if (checkFromId == 0 || checkToId == 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested account not found.");
                }else{

                    service.transfer(amountInt, fromAccountIdInt, toAccountIdInt);
                    response.setStatus(HttpServletResponse.SC_OK);
                }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      }
    }















