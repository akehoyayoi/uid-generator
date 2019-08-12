package com.akehoyayoi.uid

import org.scalatest.{FlatSpec, Matchers}

class IdWorkerTest extends FlatSpec with Matchers {

  implicit val config = IdWorkerConfig(
    workerNodeIdBits = 22L,
    sequenceBits = 10L,
    twEpoch = "2019-08-01"
  )

  "generate id" should "increasing ids" in {

    val  worker = new IdWorker(
      workerNodeId = 1L
    )

    var lastId = 0L
    for (i <- 1 to 100) {
      val id = worker.nextId
      assert(id > lastId)
      lastId = id
    }
  }
}
