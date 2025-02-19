import unittest
from your_application.product import Product, add_product, ProductAlreadyExistsError, MissingFieldError

class TestProductModule(unittest.TestCase):
    def setUp(self):
        self.products_list = []

    def test_add_product_success(self):
        product = add_product("Laptop", "A high-end gaming laptop", 1500.0, self.products_list)
        self.assertEqual(product.name, "Laptop")
        self.assertEqual(product.description, "A high-end gaming laptop")
        self.assertEqual(product.price, 1500.0)

    def test_add_product_missing_field(self):
        with self.assertRaises(MissingFieldError):
            add_product("", "A high-end gaming laptop", 1500.0, self.products_list)
        with self.assertRaises(MissingFieldError):
            add_product("Laptop", "", 1500.0, self.products_list)
        with self.assertRaises(MissingFieldError):
            add_product("Laptop", "A high-end gaming laptop", None, self.products_list)

    def test_add_product_already_exists(self):
        add_product("Laptop", "A high-end gaming laptop", 1500.0, self.products_list)
        with self.assertRaises(ProductAlreadyExistsError):
            add_product("Laptop", "A high-end gaming laptop", 1500.0, self.products_list)

if __name__ == '__main__':
    unittest.main()
