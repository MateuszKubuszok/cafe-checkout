package cafe.checkout

object TestItems {

  val oneCola  = Item(ItemType("cola"))
  val twoColas = Item(ItemType("cola"), Quantity(2))

  val oneCoffee  = Item(ItemType("coffee"))
  val twoCoffees = Item(ItemType("coffee"), Quantity(2))

  val oneCheeseSandwich = Item(ItemType("cheese sandwich"))
  val twoCheeseSandwiches = Item(ItemType("cheese sandwich"), Quantity(2))

  val oneSteakSandwich = Item(ItemType("steak sandwich"))
  val twoSteakSandwiches = Item(ItemType("steak sandwich"), Quantity(2))
}
