import React, { useState } from 'react';
import api from '../utils/api';

const Task = ({ task, onTaskUpdated, onTaskDeleted }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [title, setTitle] = useState(task.title);
  const [description, setDescription] = useState(task.description);
  const [priority, setPriority] = useState(task.priority);
  const [dueDate, setDueDate] = useState(task.dueDate);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const { data } = await api.put(`/tasks/${task._id}`, {
        title,
        description,
        priority,
        dueDate,
      });
      onTaskUpdated(data);
      setIsEditing(false);
    } catch (error) {
      console.error('Error updating task:', error.response?.data || error.message);
    }
  };

  const handleDelete = async () => {
    try {
      await api.delete(`/tasks/${task._id}`);
      onTaskDeleted(task._id);
    } catch (error) {
      console.error('Error deleting task:', error.response?.data || error.message);
    }
  };

  const priorityClass = (priority) => {
    switch (priority) {
      case 'High':
        return 'text-red-600 font-bold';
      case 'Medium':
        return 'text-yellow-600 font-bold';
      case 'Low':
        return 'text-blue-600 font-bold';
      default:
        return '';
    }
  };

  return (
    <div className="bg-white p-4 rounded shadow-md mb-4">
      {isEditing ? (
        <form onSubmit={handleUpdate} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Title</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Description</label>
            <input
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Priority</label>
            <select
              value={priority}
              onChange={(e) => setPriority(e.target.value)}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            >
              <option value="Low">Low</option>
              <option value="Medium">Medium</option>
              <option value="High">High</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Due Date</label>
            <input
              type="date"
              value={dueDate}
              onChange={(e) => setDueDate(e.target.value)}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
          <div className="flex space-x-4">
            <button
              type="submit"
              className="py-2 px-4 bg-green-600 text-white font-semibold rounded-md shadow-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500"
            >
              Update
            </button>
            <button
              type="button"
              onClick={() => setIsEditing(false)}
              className="py-2 px-4 bg-gray-600 text-white font-semibold rounded-md shadow-md hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-gray-500"
            >
              Cancel
            </button>
          </div>
        </form>
      ) : (
        <div>
          <h3 className="text-xl font-bold">{task.title}</h3>
          <p>{task.description}</p>
          <p className={priorityClass(priority)}>{task.priority}</p>
          <p>{new Date(task.dueDate).toDateString()}</p>
          <div className="flex space-x-4 mt-2">
            <button
              onClick={() => setIsEditing(true)}
              className="py-2 px-4 bg-yellow-600 text-white font-semibold rounded-md shadow-md hover:bg-yellow-700 focus:outline-none focus:ring-2 focus:ring-yellow-500"
            >
              Edit
            </button>
            <button
              onClick={handleDelete}
              className="py-2 px-4 bg-red-600 text-white font-semibold rounded-md shadow-md hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500"
            >
              Delete
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Task;
