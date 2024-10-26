package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Receptionist;
import com.medicalcenter.receptionapi.dto.receptionist.ReceptionistRequestDto;
import com.medicalcenter.receptionapi.dto.receptionist.ReceptionistResponseDto;
import com.medicalcenter.receptionapi.dto.receptionist.ReceptionistResponseWithUserCredentialsDto;
import com.medicalcenter.receptionapi.dto.user.RegisterRequestDto;
import com.medicalcenter.receptionapi.dto.user.UserCredentialsDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.mapper.ReceptionistMapper;
import com.medicalcenter.receptionapi.repository.ReceptionistRepository;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import com.medicalcenter.receptionapi.specification.ReceptionistSpecification;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReceptionistService {

  private final ReceptionistRepository receptionistRepository;
  private final UserService userService;
  private final ReceptionistMapper receptionistMapper;

  @Cacheable(value = "receptionists", key = "'count'")
  public long count() {
    return receptionistRepository.count();
  }

  @Cacheable(
      value = "receptionists",
      key =
          "#surname + '_' + #name + '_' + #middleName + '_'"
              + "+ (#birthDate != null ? #birthDate.toString() : 'null' ) "
              + "+ '_' + '_'  + #page + '_' + #pageSize")
  public Page<ReceptionistResponseDto> findAllReceptionists(
      String surname, String name, String middleName, LocalDate birthDate, int page, int pageSize) {
    Specification<Receptionist> specs = Specification.where(null);
    if (surname != null && !surname.isBlank()) {
      specs = specs.and(ReceptionistSpecification.withSurname(surname));
    }
    if (name != null && !name.isBlank()) {
      specs = specs.and(ReceptionistSpecification.withName(name));
    }
    if (middleName != null && !middleName.isBlank()) {
      specs = specs.and(ReceptionistSpecification.withMiddleName(middleName));
    }
    if (birthDate != null) {
      specs = specs.and(ReceptionistSpecification.withBirthDate(birthDate));
    }
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    return receptionistRepository
        .findAll(specs, pageable)
        .map(receptionistMapper::receptionistToReceptionistResponseDto);
  }

  @Cacheable(value = "receptionists", key = "#id")
  @PreAuthorize("hasAnyRole('ADMIN') or #id == authentication.principal.id")
  public ReceptionistResponseDto findReceptionistById(Long id) {
    return receptionistRepository
        .findById(id)
        .map(receptionistMapper::receptionistToReceptionistResponseDto)
        .orElseThrow(ResourceNotFoundException::new);
  }

  @CacheEvict(value = "receptionists", allEntries = true)
  public ReceptionistResponseWithUserCredentialsDto saveReceptionist(
      ReceptionistRequestDto receptionistRequestDto) {
    Receptionist receptionist =
        receptionistMapper.receptionistRequestDtoToReceptionist(receptionistRequestDto);
    UserCredentialsDto userCredentialsDto = userService.generateRandomCredentials();
    RegisterRequestDto registerRequestDto =
        RegisterRequestDto.builder()
            .userCredentialsDto(userCredentialsDto)
            .role(RoleAuthority.RECEPTIONIST)
            .build();
    userService.saveUser(registerRequestDto, receptionist);
    receptionist = receptionistRepository.save(receptionist);
    return ReceptionistResponseWithUserCredentialsDto.builder()
        .receptionistResponseDto(
            receptionistMapper.receptionistToReceptionistResponseDto(receptionist))
        .userCredentialsDto(userCredentialsDto)
        .build();
  }

  @CacheEvict(value = "receptionists", allEntries = true)
  public ReceptionistResponseDto updateReceptionist(
      ReceptionistRequestDto receptionistRequestDto, Long id) {
    Receptionist updateRequestReceptionist =
        receptionistMapper.receptionistRequestDtoToReceptionist(receptionistRequestDto);
    Receptionist receptionistToUpdate =
        receptionistRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    BeanUtils.copyProperties(updateRequestReceptionist, receptionistToUpdate, "id");
    Receptionist updatedReceptionist = receptionistRepository.save(receptionistToUpdate);
    return receptionistMapper.receptionistToReceptionistResponseDto(updatedReceptionist);
  }

  @CacheEvict(value = "receptionists", allEntries = true)
  public void deleteReceptionist(Long id) {
    receptionistRepository.deleteById(id);
  }
}
