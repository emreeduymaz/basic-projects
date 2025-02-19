import React, { useState, useEffect } from 'react';
import api from '../utils/api';

const Notifications = () => {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const fetchNotifications = async () => {
      const { data } = await api.get('/tasks'); // Assume tasks have dueDate field for notifications
      const upcomingTasks = data.filter(task => {
        const dueDate = new Date(task.dueDate);
        const now = new Date();
        const diff = dueDate - now;
        return diff > 0 && diff <= 7 * 24 * 60 * 60 * 1000; // Tasks due in the next 7 days
      });

      // Sort tasks by priority: High -> Medium -> Low
      upcomingTasks.sort((a, b) => {
        const priorityOrder = { 'High': 1, 'Medium': 2, 'Low': 3 };
        return priorityOrder[a.priority] - priorityOrder[b.priority];
      });

      setNotifications(upcomingTasks);
    };
    fetchNotifications();
  }, []);

  const priorityClass = (priority) => {
    switch (priority) {
      case 'High':
        return 'bg-red-100';
      case 'Medium':
        return 'bg-yellow-100';
      case 'Low':
        return 'bg-blue-100';
      default:
        return 'bg-white';
    }
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">Notifications</h1>
      <ul className="space-y-4">
        {notifications.map(task => (
          <li key={task._id} className={`p-4 rounded shadow-md ${priorityClass(task.priority)}`}>
            <h3 className="text-xl font-semibold">{task.title}</h3>
            <p className="text-sm text-gray-600">Due on: {new Date(task.dueDate).toDateString()}</p>
            <p>{task.description}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Notifications;
