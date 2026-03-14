package edu.lk.ijse.back_end.util;

import edu.lk.ijse.back_end.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        // 1. Header එකේ "Bearer " තියෙනවද කියලා බලනවා. නැත්නම් ඊළඟ filter එකට යවනවා.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);

        try {
            username = jwtUtil.extractUsername(jwtToken);
        } catch (Exception e) {
            // Token එක වැරදි නම් හෝ expire වෙලා නම් මෙතනින් filter chain එක දිගටම යනවා (Unauthorized විදිහට)
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Username එක තියෙනවා නම් සහ දැනටමත් authenticate වෙලා නැත්නම් විතරක් ඇතුළට යනවා.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 3. Token එක valid ද කියලා check කරනවා.
            if (jwtUtil.validateToken(jwtToken)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 4. SecurityContext එකට authentication එක සෙට් කරනවා.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 5. වැදගත්ම දේ: හැම වෙලාවකම filter chain එක ඉදිරියට යවන්න ඕනේ.
        filterChain.doFilter(request, response);
    }
}