package cafe.checkout

final case class Item(itemType: ItemType, quantity: Quantity = Quantity(1))
