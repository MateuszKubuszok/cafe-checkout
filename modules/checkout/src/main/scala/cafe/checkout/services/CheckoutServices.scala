package cafe.checkout.services

import cafe.checkout.{ Item, ItemType }
import cafe.checkout.checkout.Erroring
import cats.implicits._

import scala.util.{ Failure, Success, Try }

class CheckoutServices[F[_]: Erroring] {

  private val unitPrices = Map(
    ItemType("cola") -> 50,
    ItemType("coffee") -> 100,
    ItemType("cheese sandwich") -> 200,
    ItemType("steak sandwich") -> 450
  )

  def totalCost(items: List[Item]): F[Int] =
    Try(items.map {
      case Item(itemType, quantity) =>
        unitPrices(itemType) * quantity.value
    }) match {
      case Success(value) => value.sum.pure[F]
      case Failure(error) => error.raiseError[F, Int]
    }
}
