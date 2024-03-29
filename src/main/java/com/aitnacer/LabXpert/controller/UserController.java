package com.aitnacer.LabXpert.controller;

import com.aitnacer.LabXpert.dtos.echantillon.EchantillonDto;
import com.aitnacer.LabXpert.dtos.echantillon.EchantillonUser;
import com.aitnacer.LabXpert.dtos.echantillon.EchantillonView;
import com.aitnacer.LabXpert.dtos.UtilisateurDto;
import com.aitnacer.LabXpert.entity.Utilisateur;
import com.aitnacer.LabXpert.helper.JWTHelper;
import com.aitnacer.LabXpert.service.IEchantillonService;
import com.aitnacer.LabXpert.service.impl.UserServiceImpl;
import com.aitnacer.LabXpert.utils.Constant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aitnacer.LabXpert.utils.JWTUtil.AUTH_HEADER;
import static com.aitnacer.LabXpert.utils.JWTUtil.SECRET;

@RestController
@CrossOrigin
@RequestMapping(Constant.BASE_API_URL +"user")
@AllArgsConstructor
@Slf4j
public class UserController {
    final UserServiceImpl userServiceImpl;
    final IEchantillonService echantillonService;

    @Autowired
    private JWTHelper jwtHelper;

    @GetMapping
    public ResponseEntity<List<UtilisateurDto>> getAllUser(){
        return ResponseEntity.ok(userServiceImpl.getAllUtilisateur());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> getUSerById(@PathVariable(name = "id") Long id) {
        UtilisateurDto utilisateurDto = userServiceImpl.getUtilisateurById(id);
        return ResponseEntity.ok(utilisateurDto);
    }
    @GetMapping("/{id}/echantillons")
    public ResponseEntity<EchantillonUser> getEchantillonsByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(echantillonService.getEchantillonByUserId(id));
    }
    @GetMapping("/{id}/echantillons/{echantillonCode}")
    public ResponseEntity<EchantillonDto> getEchantillonByUserIdAndCode(@PathVariable(name = "id") Long id, @PathVariable(name = "echantillonCode") String echantillonCode) {

        return ResponseEntity.ok(echantillonService.getEchantillonByUserIdByCode(id,echantillonCode));
    }

    @PostMapping
    public ResponseEntity<UtilisateurDto> createUser( @RequestBody UtilisateurDto userDTO) {
        UtilisateurDto utilisateurDto = userServiceImpl.createUtilisateur(userDTO);
        return new ResponseEntity<>(utilisateurDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDto> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UtilisateurDto userDTO)  {
        return ResponseEntity.ok(userServiceImpl.updateUtilisateur(id, userDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id)  {
        Map<String, Object> response = new HashMap<>();
        userServiceImpl.deleteUtilisateur(id);
        response.put("success", true);
        response.put("message", "User with id: " + id + " has been deleted successfully!");
        return ResponseEntity.ok(response);
    }


}
