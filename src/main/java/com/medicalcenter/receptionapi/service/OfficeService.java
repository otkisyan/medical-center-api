package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.domain.Office;
import com.medicalcenter.receptionapi.dto.office.OfficeRequestDto;
import com.medicalcenter.receptionapi.dto.office.OfficeResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.mapper.OfficeMapper;
import com.medicalcenter.receptionapi.repository.OfficeRepository;
import com.medicalcenter.receptionapi.specification.OfficeSpecification;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfficeService {

  private final OfficeRepository officeRepository;
  private final OfficeMapper officeMapper;

  @Cacheable(value = "offices", key = "'count'")
  public long count() {
    return officeRepository.count();
  }

  @Cacheable(
      value = "offices",
      key = "#name + '_' + #number + '_'" + "+ '_' + #page + '_' + #pageSize")
  public Page<OfficeResponseDto> findAllOffices(
      String name, Integer number, int page, int pageSize) {

    Specification<Office> specs = Specification.where(null);
    if (name != null) {
      specs = specs.and(OfficeSpecification.withName(name));
    }
    if (number != null) {
      specs = specs.and(OfficeSpecification.withNumber(number));
    }
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    return officeRepository.findAll(specs, pageable).map(officeMapper::officeToOfficeResponseDto);
  }

  public List<Office> findAllOffices() {
    return officeRepository.findAll();
  }

  @Cacheable(value = "offices", key = "#id")
  public OfficeResponseDto findOfficeById(Long id) {
    return officeRepository
        .findById(id)
        .map(officeMapper::officeToOfficeResponseDto)
        .orElseThrow(ResourceNotFoundException::new);
  }

  @CacheEvict(value = "offices", allEntries = true)
  public OfficeResponseDto saveOffice(OfficeRequestDto officeRequestDto) {
    Office office = officeRepository.save(officeMapper.officeRequestDtoToOffice(officeRequestDto));
    return officeMapper.officeToOfficeResponseDto(office);
  }

  @CacheEvict(value = "offices", allEntries = true)
  public OfficeResponseDto updateOffice(OfficeRequestDto officeRequestDto, Long id) {
    Office officeToUpdate =
        officeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    BeanUtils.copyProperties(officeRequestDto, officeToUpdate, "id", "doctors");
    Office office = officeRepository.save(officeToUpdate);
    return officeMapper.officeToOfficeResponseDto(office);
  }

  @CacheEvict(value = "offices", allEntries = true)
  public void deleteOffice(Long id) {
    Office officeToDelete =
        officeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    Set<Doctor> officeDoctors = officeToDelete.getDoctors();
    if (!officeDoctors.isEmpty()) {
      for (Doctor doctor : officeDoctors) {
        doctor.setOffice(null);
      }
    }
    officeRepository.deleteById(id);
  }
}
