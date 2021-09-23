package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.SeanceDao;
import com.vasilisa.cinema.entity.Seance;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllSeancesCommandTest {

    @InjectMocks
    private GetAllSeancesCommand subject;

    @Mock
    private SeanceDao mockSeanceDao;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Mock
    private List<Seance> mockSeances;

    @Captor
    ArgumentCaptor<List<Seance>> listCaptor;

    @Before
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnSeancePage() {
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute(eq("locale"))).thenReturn("test_locale");
        when(mockRequest.getParameter(eq("dateFilter"))).thenReturn("test_filter");

        when(mockSeanceDao.getAll("test_locale", "test_filter", null)).thenReturn(mockSeances);

        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SEANCES);
        CommandResult actual = subject.execute(mockRequest, mockResponse);

        verify(mockRequest).setAttribute(eq("seances"), listCaptor.capture());

        assertEquals(mockSeances, listCaptor.getValue());
        assertEquals(expected.getPage(), actual.getPage());
    }
}
