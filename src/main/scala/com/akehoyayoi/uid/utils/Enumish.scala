package com.akehoyayoi.uid.utils

trait Enumish[A] {
  val values: Set[A]
  private lazy val lookup = values.map(v ⇒ (v.toString.toUpperCase(), v)).toMap
  def fromString(in: String): A = lookup(in.toUpperCase())
  def getOpt(in: String): Option[A] = lookup.get(in)
  def isValid(in: String): Boolean = lookup.contains(in.toUpperCase())
}
