package ru.gb.Application.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.Application.models.Role;
import ru.gb.Application.models.User;
import ru.gb.Application.repositories.RoleRepository;
import ru.gb.Application.repositories.UserRepository;

import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private Role findRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @PostConstruct
    void generateData() {
        roleRepository.saveAll(
                List.of(
                        new Role("admin"),
                        new Role("reader"),
                        new Role("user")
                )
        );
        userRepository.saveAll(
                List.of(
                        new User("Admin", "123", List.of(findRoleById(1L), findRoleById(2L))),
                        new User("Victor", "123", List.of(findRoleById(2L))),
                        new User("User", "123", List.of(findRoleById(3L)))
                )
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> roleList = new LinkedList<>();
        for (Role name : user.getRoles()) {
            roleList.add(new SimpleGrantedAuthority(name.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                roleList
        );
    }
}


