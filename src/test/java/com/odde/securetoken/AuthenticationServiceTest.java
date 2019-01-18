package com.odde.securetoken;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    RsaTokenDao stubRsaTokenDao = mock(RsaTokenDao.class);
    MyLog mockMyLog = mock(MyLog.class);
    AuthenticationService target = new AuthenticationService(stubRsaTokenDao, mockMyLog);

    @Test
    public void is_valid_test() {
        givenRandomCode("000000");

        assertTrue(target.isValid("joey", "91000000"));
    }

    private void givenRandomCode(String s) {
        when(stubRsaTokenDao.getRandom(anyString())).thenReturn(s);
    }

    @Test
    public void is_not_valid() {
        givenRandomCode("000001");

        boolean actual = target.isValid("joey", "91000000");

        assertFalse(actual);
    }

    @Test
    public void log_msg_when_is_not_valid() {
        givenRandomCode("000001");

        target.isValid("joey", "91000000");

        ArgumentCaptor<SalesOrder> captor = forClass(SalesOrder.class);
        verify(mockMyLog).log(captor.capture());
        assertThat(captor.getValue()).isEqualToComparingFieldByField(new SalesOrder() {{
            setShopId(100L);
            setCompany("tmall");
        }});
    }

}
