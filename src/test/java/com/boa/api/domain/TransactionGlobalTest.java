package com.boa.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.boa.api.web.rest.TestUtil;

public class TransactionGlobalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionGlobal.class);
        TransactionGlobal transactionGlobal1 = new TransactionGlobal();
        transactionGlobal1.setId(1L);
        TransactionGlobal transactionGlobal2 = new TransactionGlobal();
        transactionGlobal2.setId(transactionGlobal1.getId());
        assertThat(transactionGlobal1).isEqualTo(transactionGlobal2);
        transactionGlobal2.setId(2L);
        assertThat(transactionGlobal1).isNotEqualTo(transactionGlobal2);
        transactionGlobal1.setId(null);
        assertThat(transactionGlobal1).isNotEqualTo(transactionGlobal2);
    }
}
