from your_application.user import User, register_user, login_user, UserAlreadyExistsError, InvalidEmailError, MissingFieldError
import unittest


class TestUserModule(unittest.TestCase):
    def setUp(self):
        self.users_list = []

    def test_register_user_success(self):
        user = register_user("testuser", "password123", "testuser@example.com", self.users_list)
        self.assertEqual(user.username, "testuser")
        self.assertEqual(user.email, "testuser@example.com")

    def test_register_user_missing_field(self):
        with self.assertRaises(MissingFieldError):
            register_user("", "password123", "testuser@example.com", self.users_list)

    def test_register_user_invalid_email(self):
        with self.assertRaises(InvalidEmailError):
            register_user("testuser", "password123", "invalidemail", self.users_list)

    def test_register_user_already_exists(self):
        register_user("testuser", "password123", "testuser@example.com", self.users_list)
        with self.assertRaises(UserAlreadyExistsError):
            register_user("testuser", "password123", "testuser@example.com", self.users_list)

    def test_login_user_success(self):
        register_user("testuser", "password123", "testuser@example.com", self.users_list)
        user = login_user("testuser", "password123", self.users_list)
        self.assertIsNotNone(user)
        self.assertEqual(user.username, "testuser")

    def test_login_user_failure(self):
        register_user("testuser", "password123", "testuser@example.com", self.users_list)
        user = login_user("wronguser", "password123", self.users_list)
        self.assertIsNone(user)

if __name__ == '__main__':
    unittest.main()
