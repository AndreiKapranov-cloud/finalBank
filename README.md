# ServletBank
Rest-сервис банка.Мы можем создавать,удалять,изменять банки,аккаунты,транзакции.Изменение аккаунтов включает в себя трансфер денег с одного аккаунта на другой.
В проекте используются сервлеты,Tomcat,PostgreSQL.

Файл SQL для создания базы данных находится в \src\main\resources\postgreSqlDatabase.sql

API:

Account.

GET/account?accountId={accountId}-Получение аккаунта с указанным Id.

GET/account-Получение всех аккаунтов.

POST/account-Добавление нового аккаунта (из тела запроса).

PUT/account?amount={amount}&fromAccountId={fromAccountId}&toAccountId={toAccountId}-Перевод суммы amount c одного аккаунта на другой.

DELETE/account?accountId={accountId}-В соответствующем аккаунте меняем значение поля isDeleted на true.

Bank.

GET/bank?accountId={accountId}-Получение банка по Id аккаунта.

GET/bank-Получение всех банков.

POST/bank-Добавление нового аккаунта (из тела запроса).

DELETE/bank?bankId={bankId}-В соответствующем банке меняем значение поля isDeleted на true.

PUT/bank?bankId={bankId}&name={name}-Изменяем сщщтветствующий банк.

Transaction.

GET/transaction?transactionId={transactionId}-Получение транзакции по Id транзакции.

GET/transaction-Получение всех транзакций.

GET/transactionsByAccount?accountId={accountId}-Получение всех транзакций аккаунта.