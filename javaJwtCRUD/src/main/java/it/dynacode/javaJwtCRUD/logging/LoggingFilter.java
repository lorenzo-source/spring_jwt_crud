//package it.dynacode.javaJwtCRUD.logging;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class LoggingFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
//
//        filterChain.doFilter(wrappedRequest, wrappedResponse);
//
//        String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
//        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
//
//        logger.info("Request URI: " + request.getRequestURI());
//        logger.info("Request Body: " + requestBody);
//        logger.info("Response Body: " + responseBody);
//
//        wrappedResponse.copyBodyToResponse(); // Make sure response is actually returned to client
//    }
//}