package com.novoseltsev.appointmentapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novoseltsev.appointmentapi.domain.dto.RegistrationUserDto;
import com.novoseltsev.appointmentapi.domain.dto.UserDto;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private UserDto userDto;

    private static User initialUser;

    private static ObjectMapper jsonMapper;

    private static String userNotFoundMessage;

    @BeforeAll
    public static void setUp() {
        initialUser = new User("John", "Johnson", "johnjohnson", "johnJohnson"
                + "@gmail.com", "Uu1234");
        initialUser.setId(1L);
        initialUser.setStatus(UserStatus.ACTIVE);
        initialUser.setRole(UserRole.STUDENT);
        jsonMapper = new ObjectMapper();
    }

    @Test
    void getUsers() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(Collections
                .singletonList(initialUser));
        List<UserDto> dtoUsers =
                Collections.singletonList(UserDto.fromUser(initialUser));
        String expectedData = jsonMapper.writeValueAsString(dtoUsers);

        String result = mockMvc.perform(get("/api/v1/appointments/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedData, result);
        Mockito.verify(userService, Mockito.times(1)).findAll();
    }

    @Test
    void getUserById() throws Exception {
        Long existedUserId = initialUser.getId();
        Long notExistedUserId = 0L;
        String defaultUrl = "/api/v1/appointments/users/";
        Mockito.when(userService.findById(existedUserId))
                .thenReturn(initialUser);
        Mockito.when(userService.findById(notExistedUserId)).thenReturn(null);

        String expectedData =
                jsonMapper.writeValueAsString(UserDto.fromUser(initialUser));

        String result = mockMvc.perform(get(defaultUrl + existedUserId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedData, result);
        Mockito.verify(userService, Mockito.times(1)).findById(existedUserId);

        expectedData = "";

        result = mockMvc.perform(get(defaultUrl + notExistedUserId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedData, result);
    }

    @Test
    void create() throws JsonProcessingException {
        Mockito.when(userService.create(initialUser)).thenReturn(initialUser);
        Mockito.when(userService.findAll()).thenReturn(Collections
                .singletonList(initialUser));

        String expectedData = jsonMapper.writeValueAsString(UserDto
                .fromUser(initialUser));
        RegistrationUserDto requestBody =
                RegistrationUserDto.fromUser(initialUser);
        String wrongRepeatedPassword = "Uu12345678";

    }

    @Test
    void update() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void markAsDeleted() {
    }

    @Test
    void deleteById() {
    }
}