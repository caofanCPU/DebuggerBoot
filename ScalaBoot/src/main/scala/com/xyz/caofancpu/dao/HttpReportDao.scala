package com.xyz.caofancpu.dao

import java.util.List

import com.xyz.caofancpu.entity.HttpReport
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

trait HttpReportDao extends CrudRepository[HttpReport, Integer] {
  @Query(value = "select * from `http_report`", nativeQuery = true)
  def findAll(): List[HttpReport] // JavaConversions

  def save(t: HttpReport): HttpReport

  def findOne(id: Integer): HttpReport

}
