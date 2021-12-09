package com.newts.newtapp.api.security;

import com.newts.newtapp.api.gateways.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository repository;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserRepository repository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // Get the request authorization header
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check whether header contains Bearer token
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validate token
        String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Fetch the user from the UserRepository
        UserDetails user = repository.findByUsername(jwtTokenUtil.getUsername(token)).orElse(null);

        if (user == null) {
            // I didn't think this could happen at first, but realized it can when a user who was recently
            // deleted tries to use a token that is still valid!
            filterChain.doFilter(request, response);
            return;
        }

        // Token is valid, so create a new type of token to give to Spring's Security context
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // set the current context holders authentication
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
