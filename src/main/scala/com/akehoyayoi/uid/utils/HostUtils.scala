package com.akehoyayoi.uid.utils

import com.akehoyayoi.uid.utils.host.{DockerUtils, NetUtils}
import com.akehoyayoi.uid.worker.{ACTUAL, CONTAINER}

import scala.util.Random

class HostUtils extends HostAssigner {

  lazy val host = {
    if(DockerUtils.isDocker)
      DockerUtils.getDockerHost
    else
      NetUtils.getLocalAddress().getOrElse("DUMMY")
  }

  lazy val port = {
    if(DockerUtils.isDocker)
      DockerUtils.getDockerPort
    else
      System.currentTimeMillis + "-" + Random.nextInt(100000)
  }

  lazy val hostType = {
    if(DockerUtils.isDocker)
      CONTAINER.id
    else
      ACTUAL.id
  }

  def getHost(): String = host
  def getPort(): String = port
  def getType(): Int = hostType
}
