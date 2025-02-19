import React, { useEffect, useState } from 'react';
import api from '../utils/api';

const ProjectManagement = () => {
  const [projects, setProjects] = useState([]);
  const [projectTasks, setProjectTasks] = useState([]);
  const [newProjectName, setNewProjectName] = useState('');
  const [selectedProject, setSelectedProject] = useState(null);
  const [newProjectTask, setNewProjectTask] = useState({
    title: '',
    description: '',
    priority: 'Medium',
    dueDate: '',
  });

  useEffect(() => {
    const fetchProjects = async () => {
      const { data } = await api.get('/projects');
      setProjects(data);
    };
    fetchProjects();
  }, []);

  const handleCreateProject = async () => {
    try {
      const { data } = await api.post('/projects', { name: newProjectName });
      setProjects([...projects, data]);
      setNewProjectName('');
    } catch (error) {
      console.error('Error creating project:', error.response?.data || error.message);
    }
  };

  const handleSelectProject = async (projectId) => {
    setSelectedProject(projectId);
    try {
      const { data } = await api.get(`/projects/${projectId}/tasks`);
      setProjectTasks(data);
    } catch (error) {
      console.error('Error fetching tasks:', error.response?.data || error.message);
    }
  };

  const handleCreateProjectTask = async (e) => {
    e.preventDefault();
    try {
      const { data } = await api.post(`/projects/${selectedProject}/tasks`, newProjectTask);
      setProjectTasks([...projectTasks, data]);
      setNewProjectTask({
        title: '',
        description: '',
        priority: 'Medium',
        dueDate: '',
      });
    } catch (error) {
      console.error('Error creating task:', error.response?.data || error.message);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">Project Management</h1>
      <div className="mb-4">
        <input
          type="text"
          value={newProjectName}
          onChange={(e) => setNewProjectName(e.target.value)}
          placeholder="New Project Name"
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
        <button
          onClick={handleCreateProject}
          className="mt-2 py-2 px-4 bg-indigo-600 text-white font-semibold rounded-md shadow-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500"
        >
          Create Project
        </button>
      </div>
      <div className="mb-4">
        <h2 className="text-2xl font-semibold">Projects</h2>
        <ul className="space-y-2">
          {projects.map((project) => (
            <li
              key={project._id}
              className={`p-4 rounded shadow-md ${selectedProject === project._id ? 'bg-indigo-100' : 'bg-white'}`}
              onClick={() => handleSelectProject(project._id)}
            >
              {project.name}
            </li>
          ))}
        </ul>
      </div>
      {selectedProject && (
        <div>
          <h2 className="text-2xl font-semibold">Tasks for {projects.find((project) => project._id === selectedProject)?.name}</h2>
          <ul className="space-y-2 mb-4">
            {projectTasks.map((task) => (
              <li key={task._id} className="bg-white p-4 rounded shadow-md">
                <h3 className="text-xl font-semibold">{task.title}</h3>
                <p>{task.description}</p>
                <p className={priorityClass(task.priority)}>{task.priority}</p>
                <p>Due on: {new Date(task.dueDate).toDateString()}</p>
              </li>
            ))}
          </ul>
          <form onSubmit={handleCreateProjectTask} className="space-y-4 p-4 bg-white rounded shadow-md">
            <div>
              <label className="block text-sm font-medium text-gray-700">Title</label>
              <input
                type="text"
                value={newProjectTask.title}
                onChange={(e) => setNewProjectTask({ ...newProjectTask, title: e.target.value })}
                required
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Description</label>
              <input
                type="text"
                value={newProjectTask.description}
                onChange={(e) => setNewProjectTask({ ...newProjectTask, description: e.target.value })}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Priority</label>
              <select
                value={newProjectTask.priority}
                onChange={(e) => setNewProjectTask({ ...newProjectTask, priority: e.target.value })}
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
                value={newProjectTask.dueDate}
                onChange={(e) => setNewProjectTask({ ...newProjectTask, dueDate: e.target.value })}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <button
              type="submit"
              className="w-full py-2 px-4 bg-indigo-600 text-white font-semibold rounded-md shadow-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500"
            >
              Add Task
            </button>
          </form>
        </div>
      )}
    </div>
  );
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

export default ProjectManagement;
