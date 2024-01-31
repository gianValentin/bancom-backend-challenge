package com.app.core.security;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.RequiredArgsConstructor;

import static com.app.core.security.entity.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] WHITE_LIST_URL = { 
		"/v2/api-docs",
		"/v3/api-docs", 
		"/v3/api-docs/**", 
		"/swagger-resources", 
		"/swagger-resources/**", 
		"/configuration/ui",
		"/configuration/security", 
		"/swagger-ui/**", 
		"/webjars/**", 
		"/swagger-ui.html"
		};

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector)
			throws Exception {
		MvcRequestMatcher.Builder mvcRequestMatcher = new MvcRequestMatcher.Builder(introspector);

		return httpSecurity.csrf(csrf -> {
			csrf.ignoringRequestMatchers(PathRequest.toH2Console()).disable();
		}).authorizeHttpRequests(authResquest -> {

			authResquest.requestMatchers(PathRequest.toH2Console()).permitAll();
			
			for(String path : WHITE_LIST_URL) {
			    authResquest.requestMatchers(mvcRequestMatcher.pattern(path)).permitAll();   
			}			

			authResquest.requestMatchers(mvcRequestMatcher.pattern("/api/v1/auth/**")).permitAll();
									
			authResquest.requestMatchers(mvcRequestMatcher.pattern(GET,"/api/v1/user/**")).permitAll();			
			authResquest.requestMatchers(mvcRequestMatcher.pattern(PUT,"/api/v1/user/**")).hasAnyAuthority(USER.name());
			authResquest.requestMatchers(mvcRequestMatcher.pattern(DELETE,"/api/v1/user/**")).hasAnyAuthority(USER.name());
			
			authResquest.requestMatchers(mvcRequestMatcher.pattern(GET,"/api/v1/post/**")).permitAll();			
			authResquest.requestMatchers(mvcRequestMatcher.pattern(POST,"/api/v1/post/**")).permitAll();
			authResquest.requestMatchers(mvcRequestMatcher.pattern(PUT,"/api/v1/post/**")).hasAnyAuthority(USER.name());
			authResquest.requestMatchers(mvcRequestMatcher.pattern(DELETE,"/api/v1/post/**")).hasAnyAuthority(USER.name());

			authResquest.anyRequest().authenticated();
		}).sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).logout(logout -> {
					logout.logoutUrl("/api/v1/auth/logout");
					logout.addLogoutHandler(logoutHandler);
					logout.logoutSuccessHandler(
							(request, response, authentication) -> SecurityContextHolder.clearContext());
				}).headers(headers -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
				.cors(Customizer.withDefaults()).build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
		configuration.setExposedHeaders(Arrays.asList("X-Get-Header"));
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
