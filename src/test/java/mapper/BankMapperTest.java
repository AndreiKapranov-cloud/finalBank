package mapper;

import com.example.finalbank.dto.BankDto;
import com.example.finalbank.entity.Bank;
import com.example.finalbank.mapper.BankMapper;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BankMapperTest {
    @Test
    void testToEntity() {
        BankDto bankDto = BankDto.builder().name("ShmyfizenBank").bankId(1).build();
        BankMapper bankMapper = new BankMapper();
        Bank bank = bankMapper.toEntity(bankDto);

        assertNotNull(bank);
        assertEquals("ShmyfizenBank", bank.getName());
        assertEquals(1, bank.getBankId());

    }

    @Test
    void testToDto() {
        Bank bank = Bank.builder().name("ShmyfizenBank").bankId(1).build();
        BankMapper bankMapper = new BankMapper();
        BankDto bankDto = bankMapper.toDto(bank);

        assertNotNull(bankDto);
        assertEquals("ShmyfizenBank", bankDto.getName());
        assertEquals(1, bankDto.getBankId());
    }

    @Test
    void testToEntityWithNull() {
        BankDto bankDto = null;
        BankMapper bankMapper = new BankMapper();

        try {
            Bank bank = bankMapper.toEntity(bankDto);
            assertNull(bank);
        } catch (NullPointerException npe) {
            assertNull(bankDto);
        }
    }
}
