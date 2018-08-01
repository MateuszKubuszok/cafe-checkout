package cafe.checkout.services

import cafe.checkout.{ Item, ItemType, Quantity }
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
      result must beEqualTo(Success(385))
    }

    "apply 10% service for food" in {
      // given
      val services = new CheckoutServices[Try]
      val items    = List(oneCheeseSandwich)

      // when
      val result = services.totalCost(items)

      // then
      result must beEqualTo(Success(220))
    }

    "apply 20% service for hot food" in {
      // given
      val services = new CheckoutServices[Try]
      val items    = List(oneSteakSandwich)

      // when
      val result = services.totalCost(items)

      // then
      result must beEqualTo(Success(540))
    }

    "apply no more than 20GBP when hot food on list" in {
      // given
      val services = new CheckoutServices[Try]
      val items    = List(twoSteakSandwiches.copy(quantity = Quantity(30)))

      // when
      val result = services.totalCost(items)

      // then
      result must beEqualTo(Success(15500))
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
