const Task = require('../models/Task');

const getTasks = async (req, res) => {
  try {
    const tasks = await Task.find({ user: req.user._id });
    res.json(tasks);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

const createTask = async (req, res) => {
  const { title, description, priority, dueDate, project } = req.body;
  try {
    const task = new Task({
      user: req.user._id,
      title,
      description,
      priority,
      dueDate,
      project: project || undefined,
    });
    const createdTask = await task.save();
    res.status(201).json(createdTask);
  } catch (error) {
    console.error('Error creating task:', error);
    res.status(500).json({ message: error.message });
  }
};

const updateTask = async (req, res) => {
  const { title, description, priority, dueDate, completed } = req.body;
  try {
    const task = await Task.findById(req.params.id);
    if (task) {
      task.title = title || task.title;
      task.description = description || task.description;
      task.priority = priority || task.priority;
      task.dueDate = dueDate || task.dueDate;
      task.completed = completed !== undefined ? completed : task.completed;
      const updatedTask = await task.save();
      res.json(updatedTask);
    } else {
      res.status(404).json({ message: 'Task not found' });
    }
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

const deleteTask = async (req, res) => {
  try {
    const task = await Task.findByIdAndDelete(req.params.id);
    if (task) {
      res.json({ message: 'Task removed' });
    } else {
      res.status(404).json({ message: 'Task not found' });
    }
  } catch (error) {
    console.error('Error deleting task:', error);
    res.status(500).json({ message: error.message });
  }
};

module.exports = { getTasks, createTask, updateTask, deleteTask };
