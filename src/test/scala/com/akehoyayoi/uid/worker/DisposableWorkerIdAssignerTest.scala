package com.akehoyayoi.uid.worker

import com.akehoyayoi.uid.utils.HostAssigner
import org.scalatest.{FlatSpec, Matchers}
import io.getquill._

class DisposableWorkerIdAssignerTest extends FlatSpec with Matchers {

  lazy val ctx = new H2JdbcContext(SnakeCase, "testH2DB")

  "assigner work" should "test" in {

    val host1 = new HostAssigner {
      override def getHost(): String = "HOST1"
      override def getPort(): String = "PORT1"
      override def getType(): Int = 1
    }

    val host2 = new HostAssigner {
      override def getHost(): String = "HOST2"
      override def getPort(): String = "PORT2"
      override def getType(): Int = 1
    }

    val assigner1 = new DisposableWorkerIdAssigner(ctx, host1)

    val id1 = assigner1.assignWorkerId
    println("Id = " + id1)
    id1 should not be 0L

    val assigner2 = new DisposableWorkerIdAssigner(ctx, host2)

    val id2 = assigner2.assignWorkerId
    println("Id = " + id2)

    // different id from different host
    id2 should not be id1

    // same id from same host
    assigner1.assignWorkerId should be (id1)
    assigner2.assignWorkerId should be (id2)
  }
}
