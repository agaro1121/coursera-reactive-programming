package week1

import org.scalacheck.Gen
import org.scalacheck.Prop.{forAll, propBoolean, all}




/**
  * Created by anthonygaro on 4/19/16.
  */
object ScalaCheckPlayground extends App {

  val propConcatLists = forAll { (l1: List[Int], l2: List[Int]) =>
    l1.size + l2.size == (l1 ::: l2).size }
  propConcatLists.check

  val propSqrt = forAll { (n: Int) => scala.math.sqrt(n*n) == n }
  propSqrt.check

  val propReverseList = forAll { l: List[String] => l.reverse.reverse == l }
  propReverseList.check

  val propConcatString = forAll { (s1: String, s2: String) =>
    (s1 + s2).endsWith(s2)
  }
  propConcatString.check

  /** Custom Generator */
  val smallInteger = Gen.choose(0,100)
  val propSmallInteger = forAll(smallInteger) { n =>
    n >= 0 && n <= 100
  }
  propSmallInteger.check
  /** ****************/

  val propMakeList = forAll { n: Int =>
      (n >= 0 && n < 10000) ==> (List.fill(n)("").length == n)
    }
  propMakeList.check


  /**
    * If the implication operator is given a condition that is hard or
    * impossible to fulfill, ScalaCheck might not find enough passing
    * test cases to state that the property holds. In the following
    * trivial example, all cases where n is non-zero will be thrown away
    */
  val propTrivial = forAll { n: Int =>
      (n == 0) ==> (n == 0)
     }
  propTrivial.check

  //Labels
  def myMagicFunction(n: Int, m: Int) = n + m + 1
  val complexProp = forAll { (m: Int, n: Int) =>
    val res = myMagicFunction(n, m)
    (res >= m) :| "result > #1" &&
      (res >= n) :| "result > #2" &&
      (res < m + n) :| "result not sum"
  }
  complexProp.check

  val propMul = forAll { (n: Int, m: Int) =>
    val res = n*m
    ("evidence = " + res) |: all(
      "div1" |: m != 0 ==> (res / m == n),
      "div2" |: n != 0 ==> (res / n == m),
      "lt1"  |: res > m,
      "lt2"  |: res > n
    )
  }
  propMul.check

}
