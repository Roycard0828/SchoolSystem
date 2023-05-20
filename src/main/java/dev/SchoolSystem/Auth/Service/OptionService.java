package dev.SchoolSystem.Auth.Service;

import dev.SchoolSystem.Auth.DTO.NewOptionDTO;
import dev.SchoolSystem.Auth.Entity.Option;
import dev.SchoolSystem.Auth.Entity.Role;
import dev.SchoolSystem.Auth.Repository.OptionRepository;
import dev.SchoolSystem.Auth.Repository.RoleRepository;
import dev.SchoolSystem.Auth.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OptionService {

    @Autowired
    private final OptionRepository optionRepository;
    @Autowired
    private final RoleRepository roleRepository;

    public Option createOption(NewOptionDTO optionDTO){
        Role role = roleRepository.findByRoleName(optionDTO.getRoleName());
        Option option = new Option(optionDTO.getName(), optionDTO.getPath(), role);

        return optionRepository.save(option);
    }
}
