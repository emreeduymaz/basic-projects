# test_integration.py
import unittest
from your_application.user import User
from your_application.product import Product
from your_application.order import Order

class TestIntegration(unittest.TestCase):
    def setUp(self):
        self.users_db = {}
        self.products_db = {}
        self.orders_db = {}

    def test_user_product_order_integration(self):
        user = User.register(self.users_db, 'testuser', 'password')
        product = Product.add_product(self.products_db, 1, 'Laptop', 1000, 10)
        order = Order.create_order(self.orders_db, 1, user, product, 2)

        self.assertEqual(user.username, 'testuser')
        self.assertEqual(product.name, 'Laptop')
        self.assertEqual(order.quantity, 2)
        self.assertEqual(product.stock, 8)

if __name__ == '__main__':
    unittest.main()
