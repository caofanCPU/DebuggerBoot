package com.xyz.caofancpu.controller

import com.alibaba.fastjson.JSONObject
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
  * FileName: ScalaBootController
  * Author:   caofanCPU
  * Date:     2018/12/19 18:59
  */
@RestController
class ScalaBootController {

  @RequestMapping(value = Array("/index"))
  def index(): JSONObject = {
    val json = new JSONObject
    json.put("code", 0)
    json.put("data", "success")
    json
  }
}
