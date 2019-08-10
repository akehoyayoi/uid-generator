package com.akehoyayoi.uid.utils

import java.net.{InetAddress, NetworkInterface}

object NetUtils {

  import scala.collection.JavaConverters._

  def getLocalInetAddress(): Option[InetAddress] = {
    val interfaces = NetworkInterface.getNetworkInterfaces().asScala

    interfaces.filterNot(_.isLoopback).flatMap { interface: NetworkInterface ⇒
      val addresses = interface.getInetAddresses.asScala
      addresses.filter { address: InetAddress ⇒
        !address.isLinkLocalAddress && !address.isLoopbackAddress && !address.isAnyLocalAddress
      }
    }.toList.headOption
  }

  def getLocalAddress(): Option[String] = getLocalInetAddress().map(_.getHostAddress())
}
