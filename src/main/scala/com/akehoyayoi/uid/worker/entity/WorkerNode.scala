package com.akehoyayoi.uid.worker.entity

import java.util.Date

case class WorkerNode(
  id: Long,
  hostName: String,
  port: String,
  `type`: Int,
  launchDate: Date = new Date(),
  modified: Date = new Date(),
  created: Date = new Date())
