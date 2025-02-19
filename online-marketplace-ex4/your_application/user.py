# user.py
class User:
    def __init__(self, username, password):
        self.username = username
        self.password = password

    @staticmethod
    def register(users_db, username, password):
        if username in users_db:
            raise ValueError("User already exists")
        users_db[username] = User(username, password)
        return users_db[username]

    @staticmethod
    def login(users_db, username, password):
        user = users_db.get(username)
        if user and user.password == password:
            return user
        raise ValueError("Invalid username or password")
