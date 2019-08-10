package com.akehoyayoi.uid

case class IdWorkerConfig(
  val workerNodeIdBits: Long,
  val sequenceBits: Long,
  val twEpoch: String)

class IdWorker(val workerNodeId: Long,
               var sequence: Long = 0L)(
              implicit config: IdWorkerConfig
              ) {

  lazy val twEpoch: Long = {
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
    val date = format.parse(config.twEpoch)
    date.getTime()
  }
  lazy val maxWorkerNodeId: Long = -1L ^ (-1L << config.workerNodeIdBits)
  lazy val workerNodeIdShift = config.sequenceBits
  lazy val timestampLeftShift = config.sequenceBits + config.workerNodeIdBits
  lazy val sequenceMask = -1L ^ (-1L << config.sequenceBits)

  var lastTimestamp = -1L

  // sanity check for workerId
  if (workerNodeId > maxWorkerNodeId || workerNodeId < 0) {
    throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0".format(maxWorkerNodeId))
  }

  def nextId(): Long = synchronized {
    var timestamp = timeGen()

    if (timestamp < lastTimestamp) {
      throw new IllegalArgumentException("Clock moved backwards.  Refusing to generate id for %d milliseconds".format(
        lastTimestamp - timestamp))
    }

    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask
      if (sequence == 0) {
        timestamp = tilNextMillis(lastTimestamp)
      }
    } else {
      sequence = 0
    }

    lastTimestamp = timestamp
    ((timestamp - twEpoch) << timestampLeftShift) |
      (workerNodeId << workerNodeIdShift) |
      sequence
  }

  protected def tilNextMillis(lastTimestamp: Long): Long = {
    var timestamp = timeGen()
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen()
    }
    timestamp
  }

  protected def timeGen(): Long = System.currentTimeMillis()
}
