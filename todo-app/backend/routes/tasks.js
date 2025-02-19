const express = require('express');
const { getTasks, createTask, updateTask, deleteTask } = require('../controllers/taskController');
const { protect } = require('../middlewares/auth');
const router = express.Router();

router.route('/').get(protect, getTasks).post(protect, createTask); // Ensure the POST route is defined here
router.route('/:id').put(protect, updateTask).delete(protect, deleteTask);

module.exports = router;
