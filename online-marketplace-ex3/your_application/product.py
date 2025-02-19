class ProductAlreadyExistsError(Exception):
    pass


class MissingFieldError(Exception):
    pass


class Product:
    def __init__(self, name, description, price):
        self.name = name
        self.description = description
        self.price = price


def add_product(name, description, price, products_list):
    # Check if any required field is missing
    if not name or not description or not price:
        raise MissingFieldError("Name, description, and price are required.")

    # Check if product name already exists
    for product in products_list:
        if product.name == name:
            raise ProductAlreadyExistsError(f"The product '{name}' already exists.")

    # Create a new product and add to the list
    new_product = Product(name, description, price)
    products_list.append(new_product)
    return new_product
