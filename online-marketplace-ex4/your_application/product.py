# product.py
class Product:
    def __init__(self, product_id, name, price, stock):
        self.product_id = product_id
        self.name = name
        self.price = price
        self.stock = stock

    @staticmethod
    def add_product(products_db, product_id, name, price, stock):
        if product_id in products_db:
            raise ValueError("Product already exists")
        products_db[product_id] = Product(product_id, name, price, stock)
        return products_db[product_id]

    @staticmethod
    def update_stock(products_db, product_id, stock):
        product = products_db.get(product_id)
        if not product:
            raise ValueError("Product not found")
        product.stock = stock
        return product
