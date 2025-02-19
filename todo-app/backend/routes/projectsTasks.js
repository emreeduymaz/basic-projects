const express = require('express');
const { getProjects, createProject, updateProject, deleteProject } = require('../controllers/projectController');
const { getProjectTasks, createProjectTask, updateProjectTask, deleteProjectTask } = require('../controllers/projectTasks');

const { protect } = require('../middlewares/auth');
const router = express.Router();

router.route('/:id/tasks').get(protect, getProjectTasks).post(protect, createProjectTask);
router.route('/:id/tasks').put(protect, updateProjectTask).delete(protect, deleteProjectTask);

module.exports = router;
