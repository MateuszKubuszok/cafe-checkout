package cafe.checkout.services

import cafe.checkout.{ Item, ItemType }
import cats.implicits._
import org.specs2.mutable.Specification
import cafe.checkout.TestItems._

import scala.util.{ Failure, Success, Try }

class CheckoutServicesSpec extends Specification {

  "CheckoutServices.totalCost" should {

    "calculate the total cost of drinks and foods" in {
      // given
      val services = new CheckoutServices[Try]
      val items    = List(oneCola, oneCoffee, oneCheeseSandwich)

      // when
      val result = services.totalCost(items)

      // then
      result must beEqualTo(Success(350))
    }

    "return error if some item has no price defined" in {
      // given
      val services = new CheckoutServices[Try]
      val items    = List(Item(ItemType("undefined")))

      // when
      val result = services.totalCost(items)

      // then
      result must beAnInstanceOf[Failure[Int]]
    }
  }
}
