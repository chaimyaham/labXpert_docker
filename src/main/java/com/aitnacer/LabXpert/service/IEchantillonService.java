package com.aitnacer.LabXpert.service;

import com.aitnacer.LabXpert.dtos.echantillon.EchantillonRequestDto;
import com.aitnacer.LabXpert.dtos.echantillon.EchantillonDto;
import com.aitnacer.LabXpert.dtos.patient.PatientEchantillonDto;

import java.util.List;

public interface IEchantillonService {

    List<EchantillonDto> getAllEchantillons();

    EchantillonDto getEchantillonsById(Long id);

    EchantillonDto createEchantillon(EchantillonRequestDto echantillonRequestDto);

    EchantillonDto updateEChantillon(Long id, EchantillonDto  echantillonDto);

    void deleteEchantillon(Long id);
    PatientEchantillonDto getEchantillonsByPatientId(long patientId);
}
