package dev.SchoolSystem.Auth.Service;

import dev.SchoolSystem.Auth.DTO.NewOptionDTO;
import dev.SchoolSystem.Auth.Entity.Option;
import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Entity.User;
import dev.SchoolSystem.Auth.Enums.RoleName;
import dev.SchoolSystem.Auth.Repository.OptionRepository;
import dev.SchoolSystem.Auth.Repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private OptionService underTest;

    @BeforeEach
    void setUp() {
        underTest = new OptionService(optionRepository, roleRepository);
    }

    @Test
    void createOptionAndAssignRoleTest(){
        //given
        Role role = new Role(RoleName.ROLE_TEACHER);
        NewOptionDTO newOption = new NewOptionDTO("Assign notes",
                "/assign/notes", RoleName.ROLE_TEACHER);
        when(roleRepository.findByRoleName(newOption.getRoleName())).thenReturn(role);
        //then
        underTest.createOption(newOption);
        //then
        ArgumentCaptor<Option> optionArgumentCaptor =
                ArgumentCaptor.forClass(Option.class);
        verify(roleRepository).findByRoleName(newOption.getRoleName());
        verify(optionRepository).save(optionArgumentCaptor.capture());
        Option capturedOption = optionArgumentCaptor.getValue();
        System.out.println(capturedOption.getRole());
        assertNotNull(capturedOption);
        assertEquals(capturedOption.getRole().getRoleName(), RoleName.ROLE_TEACHER);
    }
}