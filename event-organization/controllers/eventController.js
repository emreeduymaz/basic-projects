const Event = require('../models/event');

exports.getEvents = async (req, res) => {
  try {
    const events = await Event.find({ userId: req.session.userId });
    res.render('events', { events });
  } catch (err) {
    res.status(500).send(err);
  }
};

exports.createEvent = async (req, res) => {
  try {
    const { name, date, guestList } = req.body;
    const event = new Event({ name, date, guestList: guestList.split(','), userId: req.session.userId });
    await event.save();
    res.redirect('/events');
  } catch (err) {
    res.status(500).send(err);
  }
};

exports.showCreateForm = (req, res) => {
  res.render('newEvent');
};

exports.showEditForm = async (req, res) => {
  try {
    const event = await Event.findOne({ _id: req.params.id, userId: req.session.userId });
    if (event) {
      res.render('editEvent', { event });
    } else {
      res.status(404).send('Event not found');
    }
  } catch (err) {
    res.status(500).send(err);
  }
};

exports.updateEvent = async (req, res) => {
  try {
    const { id } = req.params;
    const { name, date, guestList } = req.body;
    await Event.findOneAndUpdate({ _id: id, userId: req.session.userId }, { name, date, guestList: guestList.split(',') });
    res.redirect('/events');
  } catch (err) {
    res.status(500).send(err);
  }
};

exports.deleteEvent = async (req, res) => {
  try {
    await Event.findOneAndDelete({ _id: req.params.id, userId: req.session.userId });
    res.redirect('/events');
  } catch (err) {
    res.status(500).send(err);
  }
};
