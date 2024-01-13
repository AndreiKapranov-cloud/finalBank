package mapper;

import com.example.finalbank.dto.TransactionDto;
import com.example.finalbank.entity.Transaction;
import com.example.finalbank.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransactionMapperTest {
    @Test
    void testToEntity() {
        TransactionDto transactionDto = TransactionDto.builder().transactionId(1).amount(1).currency("USD").build();
        TransactionMapper transactionMapper = new TransactionMapper();
        Transaction transaction = transactionMapper.toEntity(transactionDto);

        assertNotNull(transaction);
        assertEquals(1, transaction.getTransactionId());

        assertEquals("USD", transaction.getCurrency());
    }

    @Test
    void testToDto() {
        Transaction transaction = Transaction.builder().transactionId(1).amount(1).currency("USD").build();
        TransactionMapper transactionMapper = new TransactionMapper();
        TransactionDto transactionDto = transactionMapper.toDto(transaction);

        assertNotNull(transactionDto);
        assertEquals(1, transactionDto.getTransactionId());

        assertEquals("USD", transactionDto.getCurrency());
    }

    @Test
    void testToEntityWithNull() {
        TransactionDto transactionDto = null;
        TransactionMapper transactionMapper = new TransactionMapper();
        try {
            Transaction transaction = transactionMapper.toEntity(transactionDto);
            assertNull(transaction);
        } catch (NullPointerException npe) {
            assertNull(transactionDto);
        }
    }
}

