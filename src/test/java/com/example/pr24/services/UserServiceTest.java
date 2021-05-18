package com.example.pr24.services;

import com.example.pr24.dao.UserDAO;
import com.example.pr24.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDAO userDAO;
    @Captor
    ArgumentCaptor<User> captor;

    @Test
    public void getUser() {
        User user = new User();
        user.setUsername("Vasya");
        User user2 = new User();
        user2.setUsername("Dima");

        Mockito.when(userDAO.findByUsername("Vasya")).thenReturn(user);
        Mockito.when(userDAO.findByUsername("Dima")).thenReturn(user2);

        UserService userService = new UserService(userDAO);

        Assertions.assertEquals(
                "Vasya",
                userService.getUser("Vasya").getUsername()
        );
        Assertions.assertEquals(
                "Dima",
                userService.getUser("Dima").getUsername()
        );

    }

    @Test
    public void save() {
        User user = new User();
        user.setUsername("Vitya");

        UserService userService = new UserService(userDAO);
        userService.save(user);

        Mockito.verify(userDAO).save(captor.capture());

        User captured = captor.getValue();
        Assertions.assertEquals("Vitya", captured.getUsername());
    }
}
