package com.supermy.scala.web

import java.util.concurrent.TimeUnit

import com.supermy.scala.Message
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}

/**
  * Created by moyong on 16/6/18.
  */


@RestController
//@RequestMapping(Array("/apiscala"))
class ApiController {

  @RequestMapping(value = Array("/helloscala"), method = Array(RequestMethod.GET))
  @ResponseBody
  def hello(): Message = {
    TimeUnit.SECONDS.sleep(6)
    val message = new Message()
    message.value = "Hello, Scala for Spring!"
    message
  }
}