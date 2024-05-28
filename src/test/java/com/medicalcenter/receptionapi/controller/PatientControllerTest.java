package com.medicalcenter.receptionapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medicalcenter.receptionapi.config.WebSecurityConfig;
import com.medicalcenter.receptionapi.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(PatientController.class)
@Import(WebSecurityConfig.class)
// @AutoConfigureMockMvc(addFilters = false)
public class PatientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

   /* @Test
    public void PatientController_Save_ReturnCreated() throws Exception {
        PatientResponseDto patientRequestDto = PatientResponseDto.builder()
                .name("Name")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .id(1L)
                .name("Name")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        when(patientService.savePatient(any(PatientResponseDto.class))).thenReturn(patientResponseDto);
        this.mockMvc.perform(post("/patients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }*/

  /*  @Test
    public void PatientController_FindAll_ReturnPagePatientResponseDto() throws Exception {
        Specification<Patient> patientSpec = Specification.where(null);
        int page = 1;
        int pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        List<Patient> patientsList = Arrays.asList(
                Patient.builder()
                        .name("John")
                        .surname("Doe")
                        .middleName("William")
                        .address("123 Main Street")
                        .phone("555-1234")
                        .messengerContact("john.doe@example.com")
                        .birthDate(LocalDate.of(1990, 5, 15))
                        .preferentialCategory("None")
                        .build(),
                Patient.builder()
                        .name("Jane")
                        .surname("Smith")
                        .middleName("Marie")
                        .address("456 Oak Avenue")
                        .phone("555-5678")
                        .messengerContact("jane.smith@example.com")
                        .birthDate(LocalDate.of(1985, 8, 22))
                        .preferentialCategory("Senior Citizen")
                        .build());
        Page<Patient> patientPage = new PageImpl<>(patientsList, pageable, patientsList.size());
        Page<PatientDto> patientResponseDtoPage = patientPage.map(PatientDto::ofEntity);
        when(patientService.count()).thenReturn(2L);
        when(patientService.findAllPatients(patientSpec, page, pageSize)).thenReturn(patientResponseDtoPage);
        this.mockMvc.perform(get("/patients")
                        .with(csrf())
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(patientResponseDtoPage.getContent().size()))
                );
    }*/

   /* @Test
    public void PatientController_FindById_ReturnPatientResponseDto() throws Exception {
        Long patientId = 1L;
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .id(1L)
                .name("Name")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        when(patientService.findPatientById(patientId)).thenReturn(patientResponseDto);
        this.mockMvc.perform(get("/patients/{id}", patientId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", org.hamcrest.CoreMatchers.is(patientId.intValue())));
    }*/

    /*@Test
    public void PatientController_Update_ReturnPatientResponseDto() throws Exception {
        Long patientId = 1L;
        PatientResponseDto patientRequestDto = PatientResponseDto.builder()
                .name("Enam")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .id(1L)
                .name("Enam")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        when(patientService.updatePatient(patientRequestDto, patientId)).thenReturn(patientResponseDto);
        this.mockMvc.perform(put("/patients/{id}", patientId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDto))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", org.hamcrest.CoreMatchers.is(patientResponseDto.getId().intValue())))
                .andExpect(jsonPath("$.name", org.hamcrest.CoreMatchers.is(patientResponseDto.getName())));
    }

    @Test
    public void PatientController_Delete_ReturnVoid() throws Exception {
        Long patientId = 1L;
        doNothing().when(patientService).deletePatient(patientId);
        mockMvc.perform(delete("/patients/{id}", patientId)
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        verify(patientService, times(1)).deletePatient(patientId);
    }*/
}
