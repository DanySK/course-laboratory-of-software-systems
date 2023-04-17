import it.unibo.test.Sometrait
import org.scalatest.funsuite.AnyFunSuite

class MyTests extends AnyFunSuite:
  test("An empty Set should have size 0") {
    println("Test1")
    assert(Set.empty.isEmpty)
  }

