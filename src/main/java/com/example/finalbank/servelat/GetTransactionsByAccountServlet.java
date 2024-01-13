package com.example.finalbank.servelat;

import com.example.finalbank.dto.AccountDto;
import com.example.finalbank.dto.TransactionDto;
import com.example.finalbank.repository.AccountDao;
import com.example.finalbank.repository.TransactionDao;
import com.example.finalbank.repository.impl.AccountDaoImpl;
import com.example.finalbank.repository.impl.TransactionDaoImpl;
import com.example.finalbank.service.AccountService;
import com.example.finalbank.service.AccountServiceImpl;
import com.example.finalbank.service.TransactionService;
import com.example.finalbank.service.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;


@WebServlet(urlPatterns = "/transactionsByAccount", name = "GetTransactionsByAccountServlet")
public class GetTransactionsByAccountServlet extends HttpServlet {
    public GetTransactionsByAccountServlet() throws SQLException {
    }
    AccountDao accountDao = new AccountDaoImpl();
    TransactionDao transactionDao = new TransactionDaoImpl();
    TransactionService service = new TransactionServiceImpl(transactionDao);
    AccountService accountService = new AccountServiceImpl(accountDao);
    ObjectMapper mapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stringId = request.getParameter("accountId");
        response.setContentType("application/json");

        if (stringId == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transactions not found.Enter accountId parameter.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Transactions not found.Enter accountId parameter.");
        } else {
            int accountId = Integer.parseInt(stringId);

            try {
                AccountDto accountDto = accountService.getAccountById(accountId);
                int checkId = accountDto.getAccountId();
                if (checkId == 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Account not found with accountId " + accountId);
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Account not found with accountId " + accountId);

                }
                        List<TransactionDto> transactionDtos = service.getTransactionsByAccount(accountId);

                        if (transactionDtos == null) {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested transactions not found.");
                            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                            response.getWriter().write("Transactions not found.");
                        } else {
                            String json = mapper.writeValueAsString(transactionDtos);
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write(json);
                        }
                    } catch (NumberFormatException e) {
                        response.setStatus(SC_BAD_REQUEST);
                        response.getWriter().write("ID should be a number.");
                    }
                }

            }
    }
