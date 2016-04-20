package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._
import Math._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min1of2") = forAll { (a: Int, b: Int) =>
    val h = insert(a, empty)
    val hi = insert(b, h)
    val smaller = if(a < b) a else b
    findMin(hi) == smaller
  }



  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h))==m
  }

  lazy val genMap: Gen[Map[Int,Int]] = for {
    k <- arbitrary[Int]
    v <- arbitrary[Int]
    m <- oneOf(const(Map.empty[Int,Int]), genMap)
  } yield m.updated(k, v)

  lazy val genHeap: Gen[H] = for {
    n <- arbitrary[A]
    h <- frequency((1, const(empty)), (9, genHeap))
  } yield insert(n, h)

  //If you insert an element into an empty heap, then delete the minimum, the resulting heap should be empty.
  property("insert1delete1") = forAll { n: Int =>
    isEmpty(deleteMin(insert(n, empty)))
  }

  property("hint3") = forAll { (h: H) =>
    def isSorted(h: H): Boolean =
      if (isEmpty(h)) true
      else {
        val m = findMin(h)
        val h2 = deleteMin(h)
        isEmpty(h2) || (m <= findMin(h2) && isSorted(h2))
      }
    isSorted(h)
  }

  //Finding a minimum of the melding of any two heaps should return a minimum of one or the other.
  property("hint4") = forAll { (h1: H, h2: H) =>
    findMin(meld(h1, h2)) == min(findMin(h1), findMin(h2))
  }

  property("meld") = forAll { (h1: H, h2: H) =>
    def heapEqual(h1: H, h2: H): Boolean =
      if (isEmpty(h1) && isEmpty(h2)) true
      else {
        val m1 = findMin(h1)
        val m2 = findMin(h2)
        m1 == m2 && heapEqual(deleteMin(h1), deleteMin(h2))
      }
    heapEqual(meld(h1, h2),
      meld(deleteMin(h1), insert(findMin(h1), h2)))
  }

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

}
