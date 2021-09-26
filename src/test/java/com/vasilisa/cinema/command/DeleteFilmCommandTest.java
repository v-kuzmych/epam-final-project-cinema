package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.FilmDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class DeleteFilmCommandTest {

    @InjectMocks
    private DeleteFilmCommand subject;

    @Mock
    private FilmDao mockFilmDao;

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
    public void shouldReturnFilmsListPageAfterDeleteFilm() {
        when(mockRequest.getParameter(anyString())).thenReturn("55");
        when(mockFilmDao.delete(anyInt())).thenReturn(true);

        CommandResult expected = new CommandResult(CommandResult.ResponseType.REDIRECT, Path.COMMAND_SHOW_FILMS_LIST);
        CommandResult actual = subject.execute(mockRequest, mockResponse);

        assertEquals(expected.getPage(), actual.getPage());
    }

    @Test
    public void shouldReturnErrorMessageWhenCantDeleteFilm() {
        when(mockRequest.getParameter(anyString())).thenReturn("55");
        when(mockFilmDao.delete(anyInt())).thenReturn(false);

        subject.execute(mockRequest, mockResponse);

        verify(mockRequest).setAttribute(eq("errorMessage"), stringCaptor.capture());
        String expected = "Failed to delete movie";
        String actual = stringCaptor.getValue();
        assertEquals(expected, actual);
    }
}