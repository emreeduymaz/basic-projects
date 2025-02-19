# test_order.py
import unittest
from your_application.order import Order
from your_application.user import User
from your_application.product import Product

class TestOrder(unittest.TestCase):
    def setUp(self):
        self.orders_db = {}
        self.users_db = {}
        self.products_db = {}
        self.user = User.register(self.users_db, 'testuser', 'password')
        self.product = Product.add_product(self.products_db, 1, 'Laptop', 1000, 10)

    def test_create_order(self):
        order = Order.create_order(self.orders_db, 1, self.user, self.product, 2)
        self.assertIn(1, self.orders_db)
        self.assertEqual(self.orders_db[1], order)
        self.assertEqual(self.product.stock, 8)

    def test_create_order_insufficient_stock(self):
        with self.assertRaises(ValueError):
            Order.create_order(self.orders_db, 1, self.user, self.product, 20)

if __name__ == '__main__':
    unittest.main()
