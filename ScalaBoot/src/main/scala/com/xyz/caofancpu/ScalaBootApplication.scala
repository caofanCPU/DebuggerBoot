package com.xyz.caofancpu

import org.springframework.boot.SpringApplication

/**
  * FileName: ScalaBootApplication
  * Author:   caofanCPU
  * Date:     2018/12/19 19:19
  */
object ScalaBootApplication extends App {
  override def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[AppConfig], args: _*)
  }
}
