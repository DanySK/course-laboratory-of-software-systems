import it.unibo.test.{Someobject, Sometrait}
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class MyTest:

  @Test
  def emptySetShouldHaveSizeZero(): Unit =
    assertEquals(0, Set.empty.size)

  @Test
  def thisTestIsActuallyUseless(): Unit =
    val someStub = new Sometrait:
      override def f: Int = 42
      override def g: Int = ???

    assertEquals(42, someStub.f)
    assertNotEquals(Someobject.f, someStub.f)

