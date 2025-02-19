# test_user.py
import unittest
from your_application.user import User

class TestUser(unittest.TestCase):
    def setUp(self):
        self.users_db = {}

    def test_register_user(self):
        user = User.register(self.users_db, 'testuser', 'password')
        self.assertIn('testuser', self.users_db)
        self.assertEqual(self.users_db['testuser'], user)

    def test_register_existing_user(self):
        User.register(self.users_db, 'testuser', 'password')
        with self.assertRaises(ValueError):
            User.register(self.users_db, 'testuser', 'password')

    def test_login_user(self):
        User.register(self.users_db, 'testuser', 'password')
        user = User.login(self.users_db, 'testuser', 'password')
        self.assertEqual(user.username, 'testuser')

    def test_login_invalid_user(self):
        with self.assertRaises(ValueError):
            User.login(self.users_db, 'nonexistent', 'password')

if __name__ == '__main__':
    unittest.main()
