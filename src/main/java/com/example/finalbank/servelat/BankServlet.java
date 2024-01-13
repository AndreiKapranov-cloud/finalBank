package com.example.finalbank.servelat;

import com.example.finalbank.dto.BankDto;
import com.example.finalbank.repository.BankDao;
import com.example.finalbank.repository.impl.BankDaoImpl;
import com.example.finalbank.service.BankService;
import com.example.finalbank.service.BankServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;



    @WebServlet(urlPatterns = "/bank", name = "BankServlet")
    public class BankServlet extends HttpServlet {
        public BankServlet() throws SQLException, JsonProcessingException {
        }
        BankDao bankDao = new BankDaoImpl();
        BankService service = new BankServiceImpl(bankDao);

        ObjectMapper mapper = new ObjectMapper();

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            String stringId = request.getParameter("accountId");
            response.setContentType("application/json");


            if (stringId == null )  {
                List<BankDto> allBanks = service.getAllBanks();
                String json = mapper.writeValueAsString(allBanks);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(json);
            } else {


                int accountId = Integer.parseInt(stringId);
                BankDto bankDto = service.getBankByAccount(accountId);
                int checkId = bankDto.getBankId();
                if (checkId == 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested bank not found.");
                } else {
                    String bankJson = mapper.writeValueAsString(bankDto);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(bankJson);
                }

            }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            BankDto dto;
            try {
                dto = mapper.readValue(
                        req.getReader().lines().collect(Collectors.joining()), BankDto.class);

            } catch (JsonProcessingException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request body.");
                return;
            }
            resp.getWriter().write(mapper.writeValueAsString(service.saveBank(dto)));
            resp.getWriter().flush();
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }
        @Override
        protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String stringId = request.getParameter("bankId");
            response.setContentType("application/json");
            try {
                int bankId = Integer.parseInt(stringId);
                BankDto bankDto = service.getBankById(bankId);
                int checkId = bankDto.getBankId();
                if (checkId == 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested account not found.");
                } else {

                    boolean removed = service.removeBank(bankId);

                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Successfully removed: " + removed);
                }
            } catch (NumberFormatException e) {
                response.setStatus(SC_BAD_REQUEST);
                response.getWriter().write("Parameter accountId should contain only numbers.");
            }
        }
        @Override
        protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String stringId = request.getParameter("bankId");
            String name = request.getParameter("name");
            int bankId = Integer.parseInt(stringId);


            BankDto bankDto = service.getBankById(bankId);
            bankDto.setName(name);

            if (bankDto.getBankId() == 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bank ID must not be null.");
                return;
            }
            service.updateBank(bankDto);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

