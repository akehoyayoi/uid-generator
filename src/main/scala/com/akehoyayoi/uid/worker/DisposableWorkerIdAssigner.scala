package com.akehoyayoi.uid.worker

import com.akehoyayoi.uid.utils.HostAssigner
import com.akehoyayoi.uid.worker.entity.WorkerNode
import io.getquill.NamingStrategy
import io.getquill.context.jdbc.JdbcContext
import io.getquill.context.sql.idiom.SqlIdiom

class DisposableWorkerIdAssigner[I <: SqlIdiom, N <: NamingStrategy](
  ctx: JdbcContext[I, N], hostAssigner: HostAssigner) extends WorkerIdAssigner {

  import ctx._

  def assignWorkerId: Long = {
    findByHostName(hostAssigner.getHost).map(_.id).getOrElse {
      val workerNode = buildWorkerNode
      val q = ctx.quote {
        query[WorkerNode].insert(lift(workerNode)).returningGenerated(_.id)
      }
      run(q)
    }
  }

  def findByHostName(hostName: String): Option[WorkerNode] = {
    run(query[WorkerNode].filter(_.hostName == lift(hostName))).headOption
  }

  def buildWorkerNode: WorkerNode = {
    WorkerNode(
      id = 0L,
      hostName = hostAssigner.getHost,
      port = hostAssigner.getPort,
      `type` = hostAssigner.getType
    )
  }
}
