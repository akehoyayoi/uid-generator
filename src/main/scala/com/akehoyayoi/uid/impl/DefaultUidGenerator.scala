package com.akehoyayoi.uid.impl

import com.akehoyayoi.uid.{IdWorker, IdWorkerConfig, UidGenerator}
import com.akehoyayoi.uid.worker.WorkerIdAssigner

class DefaultUidGenerator(
  workerIdAssigner: WorkerIdAssigner)(
  implicit config: IdWorkerConfig
) extends UidGenerator {

  lazy val worker = new IdWorker(workerIdAssigner.assignWorkerId)
  override def getUID(): Long = worker.nextId()
}
