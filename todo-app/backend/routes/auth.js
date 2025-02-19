const express = require('express');
const { registerUser, authUser } = require('../controllers/authController');
const router = express.Router();

router.post('/register', registerUser); // Route for registering a user
router.post('/login', authUser); // Route for logging in a user

module.exports = router;
