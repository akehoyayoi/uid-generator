package com.akehoyayoi.uid.worker

import com.akehoyayoi.uid.utils.Enumish

sealed trait WorkerNodeType {
  val id: Int
}

object WorkerNodeType extends Enumish[WorkerNodeType] {
  override val values: Set[WorkerNodeType] = Set(CONTAINER, ACTUAL)
}

case object CONTAINER extends WorkerNodeType {
  override val id: Int = 1
}

case object ACTUAL extends WorkerNodeType {
  override val id: Int = 2
}
