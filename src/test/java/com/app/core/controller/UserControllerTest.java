package com.app.core.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.core.config.ModelMapperConfig;
import com.app.core.config.ValidationConfig;
import com.app.core.entity.model.UserModel;
import com.app.core.security.JwtAuthenticationFilter;
import com.app.core.service.UserService;
import com.app.core.utils.CustomCodeException;

@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@Import({ ValidationConfig.class, ModelMapperConfig.class })
public class UserControllerTest {

    private final String path = "/api/v1/user";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    final Long USER_DEMO_ONE_ID = 1L;
    final String USER_DEMO_ONE_CELLPHONE = "987654321";
    final String USER_DEMO_ONE_NAME = "giancarlo";
    final String USER_DEMO_ONE_PASSWORD = "1234";
    final String USER_DEMO_ONE_LASTNAME = "valentin";
    final String USER_DEMO_ONE_CREATEAT = "30-01-2024";
    final String USER_DEMO_ONE_LASTMODIFIED = "30-01-2024";

    final Long USER_DEMO_TWO_ID = 2L;
    final String USER_DEMO_TWO_CELLPHONE = "987654322";
    final String USER_DEMO_TWO_NAME = "maria";
    final String USER_DEMO_TWO_PASSWORD = "1234";
    final String USER_DEMO_TWO_LASTNAME = "casas";
    final String USER_DEMO_TWO_CREATEAT = "30-01-2024";
    final String USER_DEMO_TWO_LASTMODIFIED = "30-01-2024";

    final Long USER_NON_EXISTING_ID = 3L;
    final String USER_DEMO_TCELLPHONE = "987654323";
    final String USER_NON_EXISTING_NAME = "jesus";
    final String USER_NON_EXISTING_PASSWORD = "1234";
    final String USER_NON_EXISTING_LASTNAME = "torres";
    final String USER_NON_EXISTING_CREATEAT = "30-01-2024";
    final String USER_NON_EXISTING_LASTMODIFIED = "30-01-2024";

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

    @Test
    @WithMockUser
    @DisplayName("Find all case found")
    public void findAllCaseFound() throws Exception {
	UserModel newUserOne = UserModel.builder()
		.id(USER_DEMO_ONE_ID)
		.cellphone(USER_DEMO_ONE_CELLPHONE)
		.name(USER_DEMO_ONE_NAME)
		.lastname(USER_DEMO_ONE_LASTNAME)
		.password(USER_DEMO_ONE_PASSWORD)
		.createAt(formatter.parse(USER_DEMO_ONE_CREATEAT))
		.lastModified(formatter.parse(USER_DEMO_ONE_LASTMODIFIED))
		.build();

	UserModel newUserTwo = UserModel.builder()
		.id(USER_DEMO_TWO_ID)
		.cellphone(USER_DEMO_TWO_CELLPHONE)
		.name(USER_DEMO_TWO_NAME)
		.lastname(USER_DEMO_TWO_LASTNAME)
		.password(USER_DEMO_TWO_PASSWORD)
		.createAt(formatter.parse(USER_DEMO_TWO_CREATEAT))
		.lastModified(formatter.parse(USER_DEMO_TWO_LASTMODIFIED))
		.build();

	Mockito.when(userService.getAll()).thenReturn(List.of(newUserOne, newUserTwo));

	String resp = """
			{
		                          "getUserDto": [
		                              {
		                                  "id": 1,
		                                  "name": "giancarlo",
		                                  "cellphone": "987654321",
		                                  "lastname": "valentin",
		                                  "createAt": "30-01-2024",
		                                  "lastModified": "30-01-2024"
		                              },
		                              {
		                                  "id": 2,
		                                  "name": "maria",
		                                  "cellphone": "987654322",
		                                  "lastname": "casas",
		                                  "createAt": "30-01-2024",
		                                  "lastModified": "30-01-2024"
		                              }
		                          ],
		                          "size": 2
		                      }
		""";

	mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(resp));
    }

    @Test
    @WithMockUser
    @DisplayName("Find by name case found")
    public void findByNameIgnoreCaseFound() throws Exception {
	UserModel newUserOne = UserModel.builder()
		.id(USER_DEMO_ONE_ID)
		.cellphone(USER_DEMO_ONE_CELLPHONE)
		.name(USER_DEMO_ONE_NAME)
		.lastname(USER_DEMO_ONE_LASTNAME)
		.password(USER_DEMO_ONE_PASSWORD)
		.createAt(formatter.parse(USER_DEMO_ONE_CREATEAT))
		.lastModified(formatter.parse(USER_DEMO_ONE_LASTMODIFIED))
		.build();

	Mockito.when(userService.getByName(USER_DEMO_ONE_NAME)).thenReturn(newUserOne);

	mockMvc.perform(get(path.concat("/{name}/name"), USER_DEMO_ONE_NAME).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("giancarlo"));
    }

    @Test
    @WithMockUser
    @DisplayName("Find by name ignore case not found")
    public void findByNameIgnoreCaseNotFound() throws Exception {
	mockMvc.perform(get(path.concat("/{name}/name"), USER_NON_EXISTING_NAME).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(CustomCodeException.CODE_400));
    }

    @Test
    @WithMockUser( roles = {"USER"})
    @DisplayName("update case success")
    public void saveCaseSuccess() throws Exception {

	UserModel savedUser = UserModel.builder()
		.id(1L)
		.cellphone("userSaved")
		.name("usersaved")
		.lastname("usersaved")
		.password("1234")
		.createAt(formatter.parse("30-01-2024"))
		.lastModified(formatter.parse("30-01-2024"))
		.build();

	Mockito.when(userService.save(any(UserModel.class))).thenReturn(savedUser);

	mockMvc.perform(put(path.concat("/{id}"), "1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("userSaved"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.cellphone").value("userSaved"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("userSaved"))		
		.andExpect(MockMvcResultMatchers.jsonPath("$.createAt").value("30-01-2024"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.lastModified").value("30-01-2024"));
    }
}
