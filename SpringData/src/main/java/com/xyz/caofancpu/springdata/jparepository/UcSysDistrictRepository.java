package com.xyz.caofancpu.springdata.jparepository;

import com.xyz.caofancpu.springdata.model.UcSysDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * FileName: UcSysDistrictRepository
 *
 * @author: caofanCPU
 * @date: 2018/12/25 21:28
 */
@RepositoryRestResource(path = "ucSysDistrict")
public interface UcSysDistrictRepository extends JpaRepository<UcSysDistrict, Integer> {

}
