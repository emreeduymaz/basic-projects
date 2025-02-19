# test_product.py
import unittest
from your_application.product import Product

class TestProduct(unittest.TestCase):
    def setUp(self):
        self.products_db = {}

    def test_add_product(self):
        product = Product.add_product(self.products_db, 1, 'Laptop', 1000, 10)
        self.assertIn(1, self.products_db)
        self.assertEqual(self.products_db[1], product)

    def test_add_existing_product(self):
        Product.add_product(self.products_db, 1, 'Laptop', 1000, 10)
        with self.assertRaises(ValueError):
            Product.add_product(self.products_db, 1, 'Laptop', 1000, 10)

    def test_update_stock(self):
        product = Product.add_product(self.products_db, 1, 'Laptop', 1000, 10)
        updated_product = Product.update_stock(self.products_db, 1, 20)
        self.assertEqual(updated_product.stock, 20)

    def test_update_stock_nonexistent_product(self):
        with self.assertRaises(ValueError):
            Product.update_stock(self.products_db, 1, 20)

if __name__ == '__main__':
    unittest.main()
