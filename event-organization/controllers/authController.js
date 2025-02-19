const User = require('../models/user');
const bcrypt = require('bcrypt');

exports.showLoginForm = (req, res) => {
  res.render('login', { error: null });
};

exports.login = async (req, res) => {
  const { username, password } = req.body;
  const user = await User.findOne({ username });

  if (user && await bcrypt.compare(password, user.password)) {
    req.session.userId = user._id;
    res.redirect('/events');
  } else {
    res.render('login', { error: 'Invalid username or password' });
  }
};

exports.showRegisterForm = (req, res) => {
  res.render('register');
};

exports.register = async (req, res) => {
  const { username, password } = req.body;
  const hashedPassword = await bcrypt.hash(password, 10);
  const user = new User({ username, password: hashedPassword });

  await user.save();
  res.redirect('/login');
};

exports.logout = (req, res) => {
  req.session.destroy(err => {
    if (err) {
      return res.redirect('/events');
    }
    res.clearCookie('connect.sid');
    res.redirect('/login');
  });
};
