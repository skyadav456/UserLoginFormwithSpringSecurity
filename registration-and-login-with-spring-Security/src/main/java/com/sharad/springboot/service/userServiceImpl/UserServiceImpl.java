package com.sharad.springboot.service.userServiceImpl;

import com.sharad.springboot.dto.UserDto;
import com.sharad.springboot.entiry.Role;
import com.sharad.springboot.entiry.User;
import com.sharad.springboot.repository.RoleRepository;
import com.sharad.springboot.repository.UserRepository;
import com.sharad.springboot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository=roleRepository;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName()+" "+userDto.getLastName());
        user.setEmail(userDto.getEmail());
        //encrypt the password using spring security
        user.setPassword(userDto.getPassword());


        Role role= roleRepository.findByName("ROLE_ADMIN");
        if (role==null){
            role=checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);


    }

    private Role checkRoleExist(){
        Role role=new Role();
        role.setName("ROLE_ADMIN");
       return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    // to gell all user
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    //  entity to dto
    private UserDto mapToUserDto(User user){
        UserDto userDto= new UserDto();
        String [] str= user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return  userDto;
    }
}
