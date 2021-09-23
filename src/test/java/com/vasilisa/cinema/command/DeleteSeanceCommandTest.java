package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.SeanceDao;
import com.vasilisa.cinema.dao.SeanceDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class DeleteSeanceCommandTest {

    @InjectMocks
    private DeleteSeanceCommand subject;

    @Mock
    private SeanceDao mockSeanceDao;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Before
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnFilmEditPageAfterDeleteFilm() {
        when(mockRequest.getParameter(anyString())).thenReturn("1");
        when(mockRequest.getParameter(anyString())).thenReturn("55");
        when(mockSeanceDao.delete(anyInt())).thenReturn(true);

        CommandResult expected = new CommandResult(CommandResult.ResponseType.REDIRECT, "/controller?command=film_edit&tab=schedule&id=55");
        CommandResult actual = subject.execute(mockRequest, mockResponse);

        assertEquals(expected.getPage(), actual.getPage());
    }

    @Test
    public void shouldReturnErrorMessageWhenCantDeleteSeance() {
        when(mockRequest.getParameter(anyString())).thenReturn("55");
        when(mockSeanceDao.delete(anyInt())).thenReturn(false);

        subject.execute(mockRequest, mockResponse);

        verify(mockRequest).setAttribute(eq("errorMessage"), stringCaptor.capture());
        String expected = "Не вдалося видалити сеанс";
        String actual = stringCaptor.getValue();
        assertEquals(expected, actual);
    }
}
