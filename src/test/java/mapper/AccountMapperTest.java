package mapper;

import com.example.demo.dto.AccountDto;
import com.example.demo.entity.Account;
import com.example.demo.mapper.AccountMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountMapperTest {
    @Test
    void testToEntity() {
        AccountDto accountDto = AccountDto.builder().accountId(1).balance(10).bankId(2).build();
        AccountMapper accountMapper = new AccountMapper();
        Account account = accountMapper.toEntity(accountDto);

        assertNotNull(account);
        assertEquals(1, account.getAccountId());
        assertEquals(10, account.getBalance());
        assertEquals(2, account.getBankId());
    }

    @Test
    void testToDto() {
        Account account = Account.builder().accountId(2).balance(10).bankId(2).build();
        AccountMapper accountMapper = new AccountMapper();
        AccountDto accountDto = accountMapper.toDto(account);

        assertNotNull(accountDto);
        assertEquals(2, accountDto.getAccountId());
        assertEquals(2, accountDto.getBankId());
        assertEquals(10, accountDto.getBalance());
    }

    @Test
    void testToEntityWithNull() {
        AccountDto accountDto = null;
        AccountMapper accountMapper = new AccountMapper();

        try {
            Account account = accountMapper.toEntity(accountDto);
            assertNull(account);
        } catch (NullPointerException npe) {
            assertNull(accountDto);
        }
    }
}