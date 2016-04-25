package calculator

import scala.math._

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal[Double](pow(b(),2) - (4 * a() * c()))
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal[Set[Double]]{
      if(delta() < 0) Set(0.0)
      else{
        val x = ( -b() + sqrt(delta()) ) / (2 * a())
        val y = ( -b() - sqrt(delta()) ) / (2 * a())
        Set(x,y)
      }
    }
  }
}
