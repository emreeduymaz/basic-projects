import React, { useContext, useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../utils/api';
import { AuthContext } from '../context/AuthContext';
import Task from '../components/Task';
import TaskForm from '../components/TaskForm';

const MainPanel = () => {
  const [tasks, setTasks] = useState([]);
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchTasks = async () => {
      const { data } = await api.get('/tasks');
      setTasks(data);
    };
    fetchTasks();
  }, []);

  const handleTaskCreated = (newTask) => {
    setTasks((prevTasks) => [...prevTasks, newTask]);
  };

  const handleTaskUpdated = (updatedTask) => {
    setTasks((prevTasks) =>
      prevTasks.map((task) => (task._id === updatedTask._id ? updatedTask : task))
    );
  };

  const handleTaskDeleted = (taskId) => {
    setTasks((prevTasks) => prevTasks.filter((task) => task._id !== taskId));
  };

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">Welcome, {user.name}</h1>
      <button
        onClick={handleLogout}
        className="mb-4 py-2 px-4 bg-red-600 text-white font-semibold rounded-md shadow-md hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500"
      >
        Logout
      </button>
      <h2 className="text-2xl font-semibold mb-4">Your Tasks</h2>
      <TaskForm onTaskCreated={handleTaskCreated} />
      <div className="space-y-4">
        {tasks.map((task) => (
          <Task
            key={task._id}
            task={task}
            onTaskUpdated={handleTaskUpdated}
            onTaskDeleted={handleTaskDeleted}
          />
        ))}
      </div>
      <div className="mt-4 flex space-x-4">
        <Link
          to="/calendar"
          className="py-2 px-4 bg-blue-600 text-white font-semibold rounded-md shadow-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          Go to Calendar
        </Link>
        <Link
          to="/notifications"
          className="py-2 px-4 bg-green-600 text-white font-semibold rounded-md shadow-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500"
        >
          Go to Notifications
        </Link>
        <Link
          to="/projects"
          className="py-2 px-4 bg-purple-600 text-white font-semibold rounded-md shadow-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500"
        >
          Go to Projects
        </Link>
      </div>
    </div>
  );
};

export default MainPanel;
