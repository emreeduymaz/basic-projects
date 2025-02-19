# order.py
class Order:
    def __init__(self, order_id, user, product, quantity):
        self.order_id = order_id
        self.user = user
        self.product = product
        self.quantity = quantity

    @staticmethod
    def create_order(orders_db, order_id, user, product, quantity):
        if order_id in orders_db:
            raise ValueError("Order already exists")
        if product.stock < quantity:
            raise ValueError("Not enough stock")
        product.stock -= quantity
        orders_db[order_id] = Order(order_id, user, product, quantity)
        return orders_db[order_id]
