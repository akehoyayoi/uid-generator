package com.akehoyayoi.uid.worker

import com.akehoyayoi.uid.utils.{DockerUtils, NetUtils}
import com.akehoyayoi.uid.worker.entity.WorkerNode

import scala.util.Random
import io.getquill._

class DisposableWorkerIdAssigner extends WorkerIdAssigner {

  lazy val ctx = new H2JdbcContext(SnakeCase, "ctx")

  import ctx._

  def assignWorkerId: Long = {
    findByHostName(getHostName).map(_.id).getOrElse {
      val workerNode = buildWorkerNode
      val q = quote {
        query[WorkerNode].insert(lift(workerNode)).returningGenerated(_.id)
      }
      ctx.run(q)
    }
  }

  def findByHostName(hostName: String): Option[WorkerNode] = {
    run(query[WorkerNode].filter(_.hostName == lift(hostName))).headOption
  }

  def getHostName: String = if(DockerUtils.isDocker) DockerUtils.getDockerHost else NetUtils.getLocalAddress().getOrElse("DUMMY")

  def buildWorkerNode: WorkerNode = {
    if(DockerUtils.isDocker) WorkerNode(
      id = 0L,
      hostName = DockerUtils.getDockerHost,
      port = DockerUtils.getDockerPort,
      `type` = CONTAINER.id
    ) else WorkerNode(
      id = 0L,
      hostName = NetUtils.getLocalAddress().getOrElse("DUMMY"),
      port = System.currentTimeMillis + "-" + Random.nextInt(100000),
      `type` = ACTUAL.id
    )
  }
}
