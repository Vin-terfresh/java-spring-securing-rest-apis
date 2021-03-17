package io.jzheaux.springsecurity.resolutions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserRepositoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(BridgeUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("no user"));
    }

    private static class BridgeUser extends User implements UserDetails {
        public BridgeUser(User user) {
            super(user);
        }

        public List<GrantedAuthority> getAuthorities() {
            return this.userAuthorities.stream()
                    .map(UserAuthority::getAuthority)
                    .flatMap(authority -> {
                        if (authority.equals("ROLE_ADMIN")) {
                            return Stream.of(
                                    new SimpleGrantedAuthority("resolution:read"),
                                    new SimpleGrantedAuthority("resolution:write"),
                                    new SimpleGrantedAuthority(authority)
                            );
                        } else {
                            return Stream.of(new SimpleGrantedAuthority(authority));
                        }
                    })
                    .collect(Collectors.toList());
        }

        public boolean isAccountNonExpired() {
            return this.enabled;
        }

        public boolean isAccountNonLocked() {
            return this.enabled;
        }

        public boolean isCredentialsNonExpired() {
            return this.enabled;
        }
    }
}
