package com.akehoyayoi.uid.utils

trait HostAssigner {
  def getHost(): String
  def getPort(): String
  def getType(): Int
}
