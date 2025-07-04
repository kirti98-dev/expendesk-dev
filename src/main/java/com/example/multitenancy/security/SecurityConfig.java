package com.example.multitenancy.security;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import com.example.multitenancy.repository.ClientRepository;


@Configuration
public class SecurityConfig {
	
	@Autowired
	@Lazy
    private ClientRepository clientRepository;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
        	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())  // Disable CSRF for APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()  // Public APIs
                .anyRequest().authenticated())  // Secure other APIs
            .formLogin(form -> form.disable())  // Disable default form login
            .httpBasic(basic -> basic.disable()); // Disable Basic Auth (Removes default password)
//    		.addFilterBefore(tenantFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    
	/*
	 * @Bean OncePerRequestFilter tenantFilter() { return new OncePerRequestFilter()
	 * {
	 * 
	 * @Override protected void doFilterInternal(HttpServletRequest request,
	 * HttpServletResponse response, FilterChain filterChain) throws
	 * ServletException, IOException { String clientId =
	 * request.getHeader("X-Tenant-Id"); if (clientId != null) { Optional<Client>
	 * client = clientRepository.findByClientCode(clientId); if (client.isPresent())
	 * { TenantInfo tenantInfo = new TenantInfo( client.get().getClientCode(),
	 * client.get().getDatabaseUrl(), client.get().getDatabaseUsername(),
	 * client.get().getDatabasePassword()); TenantContext.setTenantInfo(tenantInfo);
	 * } else { // Handle invalid tenant ID } } try { filterChain.doFilter(request,
	 * response); } finally { TenantContext.clear(); } } }; }
	 */
    
    @Bean
    UserDetailsService userDetailsService() {
        return username -> null;  // No default user authentication
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://192.168.1.106:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Tenant-Id"));
        config.setAllowCredentials(true); // Required if sending cookies or JWT tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
