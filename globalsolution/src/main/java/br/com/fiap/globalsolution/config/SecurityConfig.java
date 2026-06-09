package br.com.fiap.globalsolution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ============================================================
    // REGRAS DE ACESSO AOS ENDPOINTS
    // ============================================================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (não necessário para APIs REST stateless)
                .csrf(csrf -> csrf.disable())

                // Define a política de sessão como STATELESS (sem sessão no servidor)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define as regras de autorização por endpoint
                .authorizeHttpRequests(auth -> auth

                        // H2 Console — liberado para todos
                        .requestMatchers("/h2-console/**").permitAll()

                        // GET — qualquer usuário autenticado pode consultar
                        .requestMatchers(HttpMethod.GET, "/api/solucoes/**").hasAnyRole("USER", "ADMIN")

                        // POST, PUT, PATCH — apenas ADMIN pode criar/alterar
                        .requestMatchers(HttpMethod.POST, "/api/solucoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/solucoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/solucoes/**").hasRole("ADMIN")

                        // DELETE — apenas ADMIN pode excluir
                        .requestMatchers(HttpMethod.DELETE, "/api/solucoes/**").hasRole("ADMIN")

                        // Qualquer outra requisição precisa estar autenticado
                        .anyRequest().authenticated()
                )

                // Habilita autenticação Basic (usuário e senha no header)
                .httpBasic(httpBasic -> {})

                // Permite renderizar o H2 Console dentro de iframes
                .headers(headers ->
                        headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    // ============================================================
    // USUÁRIOS EM MEMÓRIA
    // ============================================================
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        // Usuário comum — só pode consultar (GET)
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        // Administrador — acesso total
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // ============================================================
    // ENCODER DE SENHA (BCrypt)
    // ============================================================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
