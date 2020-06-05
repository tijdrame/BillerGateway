package com.boa.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.boa.api.web.rest.TestUtil;

public class BillerTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillerT.class);
        BillerT billerT1 = new BillerT();
        billerT1.setId(1L);
        BillerT billerT2 = new BillerT();
        billerT2.setId(billerT1.getId());
        assertThat(billerT1).isEqualTo(billerT2);
        billerT2.setId(2L);
        assertThat(billerT1).isNotEqualTo(billerT2);
        billerT1.setId(null);
        assertThat(billerT1).isNotEqualTo(billerT2);
    }
}
