package com.xyz.caofancpu.dao

import java.util.List

import com.xyz.caofancpu.entity.HttpSuite
import org.springframework.data.repository.CrudRepository

trait HttpSuiteDao extends CrudRepository[HttpSuite, Integer] {
  def findAll(): List[HttpSuite] // JavaConversions

  def save(t: HttpSuite): HttpSuite

  def findOne(id: Integer): HttpSuite

}