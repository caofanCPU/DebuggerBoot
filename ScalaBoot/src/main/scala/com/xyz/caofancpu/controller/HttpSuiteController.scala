package com.xyz.caofancpu.controller

import com.xyz.caofancpu.dao.HttpSuiteDao
import com.xyz.caofancpu.entity.HttpSuite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation._
// java-scala conversion
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping(Array("/httpsuite"))
class HttpSuiteController @Autowired()(val HttpSuiteDao: HttpSuiteDao) {

  @RequestMapping(value = {
    Array("", "/")
  })
  def list(model: Model) = {
    val HttpSuites = HttpSuiteDao.findAll
    model.addAttribute("HttpSuites", HttpSuites)
    new ModelAndView("/httpsuite/list")
  }

  @RequestMapping(Array("/newPage"))
  def goNewPage() = {
    new ModelAndView("/httpsuite/new")
  }


  @RequestMapping(value = Array("/postnew"), method = Array(RequestMethod.POST))
  @ResponseBody
  def newOne(@RequestParam(value = "name") name: String) = {
    val p = new HttpSuite
    p.name = name
    HttpSuiteDao.save(p)
  }

}