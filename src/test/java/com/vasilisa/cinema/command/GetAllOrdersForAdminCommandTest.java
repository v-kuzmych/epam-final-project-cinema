package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderDao;
import com.vasilisa.cinema.entity.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GetAllOrdersForAdminCommandTest {

    @InjectMocks
    private GetAllOrdersForAdminCommand subject;

    @Mock
    private OrderDao mockOrderDao;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Mock
    private List<Order> mockOrders;

    @Captor
    ArgumentCaptor<List<Order>> listCaptor;

    @Before
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnOrdersListPage() {
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute(eq("locale"))).thenReturn("test_locale");
        when(mockOrderDao.getAll("test_locale")).thenReturn(mockOrders);

        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ORDERS_LIST);
        CommandResult actual = subject.execute(mockRequest, mockResponse);

        verify(mockRequest).setAttribute(eq("orders"), listCaptor.capture());

        assertEquals(mockOrders, listCaptor.getValue());
        assertEquals(expected.getPage(), actual.getPage());
    }
}