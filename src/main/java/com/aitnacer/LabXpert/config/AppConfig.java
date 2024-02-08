package com.aitnacer.LabXpert.config;

import com.aitnacer.LabXpert.dtos.echantillon.EchantillonDto;
import com.aitnacer.LabXpert.entity.Echantillon;
import com.aitnacer.LabXpert.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.format.DateTimeFormatter;

@Configuration
@ComponentScan("com.aitnacer.LabXpert")
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepository userRepository;
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        modelMapper.getConfiguration().setUseOSGiClassLoaderBridging(true);
//        modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
//        modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
//        // Create a TypeMap with explicit mappings for Echantillon to EchantillonDto
//        TypeMap<Echantillon, EchantillonDto> typeMap = modelMapper.createTypeMap(Echantillon.class, EchantillonDto.class);
//        typeMap.addMapping(src -> src.getPatient().getId(), EchantillonDto::setPatientId);
//        typeMap.addMapping(src -> src.getUtilisateur().getId(), EchantillonDto::setUtilisateurId);

        return modelMapper;
    }

    //This configuration will tell Jackson to serialize LocalDateTime
    // instances into strings in the format yyyy-MM-dd'T'HH:mm:ss.

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow all origins, headers, and methods for simplicity. You may want to restrict these in a production environment.
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return username -> userRepository.findByUserName(username)
//                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
//
//    }
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authProvider =new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return  new BCryptPasswordEncoder();
//    }
}
