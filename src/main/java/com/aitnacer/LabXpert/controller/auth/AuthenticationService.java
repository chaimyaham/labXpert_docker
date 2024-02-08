//package com.aitnacer.LabXpert.controller.auth;
//
//import com.aitnacer.LabXpert.entity.UserRole;
//import com.aitnacer.LabXpert.entity.Utilisateur;
//import com.aitnacer.LabXpert.repository.UserRepository;
//import com.aitnacer.LabXpert.service.security.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.NoSuchElementException;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//    private final UserRepository repository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthenticationResponse register(RegisterRequest request) {
//        Utilisateur user = Utilisateur.builder()
//                .nom(request.getNom())
//                .prenom(request.getPrenom())
//                .Adresse(request.getAdresse())
//                .telephone(request.getTelephone())
//                .sexe(request.getSexe())
//                .userName(request.getUserName())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(UserRole.TECHNICIEN)
//                .build();
//        repository.save(user);
//       String jwtToken =jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUserName(),
//                        request.getPassword()
//                )
//        );
//       Utilisateur user =repository.findByUserName(request.getUserName()).orElseThrow(() -> new NoSuchElementException("L'utilisateur n'a pas été trouvé"));
//        String jwtToken =jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }
//}
