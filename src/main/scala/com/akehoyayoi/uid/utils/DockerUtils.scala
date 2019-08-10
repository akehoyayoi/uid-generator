package com.akehoyayoi.uid.utils

object DockerUtils {

  /** Environment param keys */
  private val ENV_KEY_HOST = "JPAAS_HOST"
  private val ENV_KEY_PORT = "JPAAS_HTTP_PORT"
  private val ENV_KEY_PORT_ORIGINAL = "JPAAS_HOST_PORT_8080"


  /** Docker host & port */
  lazy val DOCKER_HOST = {
    System.getenv(ENV_KEY_HOST)
  }
  lazy val DOCKER_PORT = {
    val port = System.getenv(ENV_KEY_PORT)
    if(port.isEmpty) System.getenv(ENV_KEY_PORT_ORIGINAL)
    else port
  }

  /** Whether is docker */
  private var IS_DOCKER = {
    !DOCKER_HOST.isEmpty() && !DOCKER_PORT.isEmpty()
  }

  /**
    * Retrieve docker host
    *
    * @return empty string if not a docker
    */
  def getDockerHost: String = DOCKER_HOST

  /**
    * Retrieve docker port
    *
    * @return empty string if not a docker
    */
  def getDockerPort: String = DOCKER_PORT

  /**
    * Whether a docker
    *
    * @return
    */
  def isDocker: Boolean = IS_DOCKER
}
