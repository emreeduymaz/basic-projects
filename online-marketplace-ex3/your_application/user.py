import re


class UserAlreadyExistsError(Exception):
    pass


class InvalidEmailError(Exception):
    pass


class MissingFieldError(Exception):
    pass


class User:
    def __init__(self, username, password, email):
        self.username = username
        self.password = password
        self.email = email


def is_valid_email(email):
    # Simple regex for validating an email
    regex = r'^\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b'
    return re.match(regex, email) is not None


def register_user(username, password, email, users_list):
    # Check if any required field is missing
    if not username or not password or not email:
        raise MissingFieldError("Username, password, and email are required.")

    # Check if email is valid
    if not is_valid_email(email):
        raise InvalidEmailError("The email address is not valid.")

    # Check if username already exists
    for user in users_list:
        if user.username == username:
            raise UserAlreadyExistsError(f"The username '{username}' is already taken.")

    # Create a new user and add to the list
    new_user = User(username, password, email)
    users_list.append(new_user)
    return new_user


def login_user(username, password, users_list):
    for user in users_list:
        if user.username == username and user.password == password:
            return user
    return None
