package cafe.checkout.services

import cafe.checkout.{ Item, ItemType, Quantity }
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
    Try((aggregateItems andThen countTotal andThen applyServiceCharge.tupled)(items)) match {
      case Success(value) => value.pure[F]
      case Failure(error) => error.raiseError[F, Int]
    }

  private val aggregateItems: List[Item] => List[Item] =
    _.groupBy(_.itemType).mapValues(i => Quantity(i.map(_.quantity.value).sum)).map(Item.tupled).toList

  private val countTotal: List[Item] => (List[Item], Int) = items =>
    items -> items.map {
      case Item(itemType, quantity) =>
        unitPrices(itemType) * quantity.value
    }.sum

  private val food          = Set(ItemType("cheese sandwich"), ItemType("steak sandwich"))
  private val hotFood       = Set(ItemType("steak sandwich"))
  private val foodCharge    = 0.1
  private val hotFoodCharge = 0.2
  private val maxCharge     = 20 * 100

  private val applyServiceCharge: (List[Item], Int) => Int = (items, total) => {

    val containsFood    = (items.map(_.itemType).toSet intersect food).nonEmpty
    val containsHotFood = (items.map(_.itemType).toSet intersect hotFood).nonEmpty

    total + ((containsHotFood, containsFood) match {
      case (true, _) => math.min((total * hotFoodCharge).toInt, maxCharge)
      case (_, true) => (total * foodCharge).toInt
      case _         => 0
    })
  }
}
