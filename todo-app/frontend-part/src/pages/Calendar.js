import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import api from '../utils/api';

const CalendarPage = () => {
  const [tasks, setTasks] = useState([]);
  const [date, setDate] = useState(new Date());

  useEffect(() => {
    const fetchTasks = async () => {
      const { data } = await api.get('/tasks');
      setTasks(data);
    };
    fetchTasks();
  }, []);

  const onDateChange = (date) => {
    setDate(date);
  };

  const getTasksForDate = (date) => {
    return tasks.filter(task => {
      const taskDate = new Date(task.dueDate).toDateString();
      return taskDate === date.toDateString();
    });
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">Calendar View</h1>
      <div className="mb-4">
        <Calendar onChange={onDateChange} value={date} className="mx-auto" />
      </div>
      <div>
        <h2 className="text-2xl font-semibold mb-2">Tasks for {date.toDateString()}</h2>
        <ul className="list-disc list-inside">
          {getTasksForDate(date).map(task => (
            <li key={task._id} className="mb-2">
              <h3 className="text-xl font-bold">{task.title}</h3>
              <p>{task.description}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default CalendarPage;
