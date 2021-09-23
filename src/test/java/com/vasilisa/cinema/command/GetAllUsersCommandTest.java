package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.UserDao;
import com.vasilisa.cinema.entity.Seance;
import com.vasilisa.cinema.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllUsersCommandTest {

    @InjectMocks
    private GetAllUsersCommand subject;

    @Mock
    private UserDao mockUserDao;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private List<User> mockUsers;

    @Captor
    ArgumentCaptor<List<User>> listCaptor;

    @Before
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnUsersListPage() {
        when(mockUserDao.getAll()).thenReturn(mockUsers);

        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__USERS_LIST);
        CommandResult actual = subject.execute(mockRequest, mockResponse);

        verify(mockRequest).setAttribute(eq("users"), listCaptor.capture());

        assertEquals(mockUsers, listCaptor.getValue());
        assertEquals(expected.getPage(), actual.getPage());
    }
}
