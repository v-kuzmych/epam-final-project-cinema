package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.LanguageDao;
import com.vasilisa.cinema.entity.Language;
import com.vasilisa.cinema.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShowAddFilmPageCommandTest {

    @InjectMocks
    private ShowAddFilmPageCommand subject;

    @Mock
    private LanguageDao mockLanguageDao;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private List<Language> mockLanguages;

    @Captor
    ArgumentCaptor<List<Language>> listCaptor;

    @Before
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAddFilmPage() {
        when(mockLanguageDao.getAll()).thenReturn(mockLanguages);

        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADD_FILM);
        CommandResult actual = subject.execute(mockRequest, mockResponse);

        verify(mockRequest).setAttribute(eq("languages"), listCaptor.capture());

        assertEquals(mockLanguages, listCaptor.getValue());
        assertEquals(expected.getPage(), actual.getPage());
    }
}
