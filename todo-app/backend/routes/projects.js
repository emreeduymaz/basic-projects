const express = require('express');
const { getProjects, createProject, updateProject, deleteProject } = require('../controllers/projectController');

const { protect } = require('../middlewares/auth');
const router = express.Router();

router.route('/').get(protect, getProjects).post(protect, createProject);
router.route('/:id').put(protect, updateProject).delete(protect, deleteProject);

module.exports = router;
