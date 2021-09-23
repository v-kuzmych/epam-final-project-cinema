package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderItemDao;
import com.vasilisa.cinema.dao.SeanceDao;
import com.vasilisa.cinema.entity.Seance;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ShowOrderPageCommandTest {

    @InjectMocks
    private ShowOrderPageCommand subject;

    @Mock
    private SeanceDao mockSeanceDao;

    @Mock
    private OrderItemDao mockOrderItemDao;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Mock
    private List<String> mockOccupiedSeats;

    @Mock
    private Seance mockSeance;

    @Captor
    ArgumentCaptor<List<String>> listCaptor;

    @Captor
    ArgumentCaptor<Seance> seanceCaptor;

    @Before
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnOrdersListPage() {
        when(mockRequest.getParameter(anyString())).thenReturn("55");
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute(eq("locale"))).thenReturn("test_locale");

        when(mockSeanceDao.get(anyInt(), eq("test_locale"))).thenReturn(mockSeance);
        when(mockOrderItemDao.getOccupiedSeatsAtSeance(anyInt())).thenReturn(mockOccupiedSeats);

        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ORDER);
        CommandResult actual = subject.execute(mockRequest, mockResponse);

        verify(mockRequest).setAttribute(eq("seance"), seanceCaptor.capture());
        verify(mockRequest).setAttribute(eq("occupiedSeats"), listCaptor.capture());

        assertEquals(mockSeance, seanceCaptor.getValue());
        assertEquals(mockOccupiedSeats, listCaptor.getValue());
        assertEquals(expected.getPage(), actual.getPage());
    }
}
