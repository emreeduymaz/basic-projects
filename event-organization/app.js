const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const session = require('express-session');
const helmet = require('helmet');

const eventRoutes = require('./routes/eventRoutes');
const authRoutes = require('./routes/authRoutes');

const app = express();

// MongoDB connection
mongoose.connect('mongodb://localhost:27017/events', {
  useNewUrlParser: true,
  useUnifiedTopology: true
})
  .then(() => {
    console.log('Connected to MongoDB');
  })
  .catch(err => {
    console.error('Failed to connect to MongoDB', err);
  });

app.set('view engine', 'ejs');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static('public'));

// Helmet for CSP headers
app.use(helmet({
  contentSecurityPolicy: {
    directives: {
      defaultSrc: ["'self'"],
      imgSrc: ["'self'", "data:"],
      scriptSrc: ["'self'", "'unsafe-inline'"],
      styleSrc: ["'self'", "'unsafe-inline'"]
    }
  }
}));

// Session setup
app.use(session({
  secret: 'your_secret_key',
  resave: false,
  saveUninitialized: true,
  cookie: { secure: false } // Set to true in production
}));

// Middleware to check authentication
function isAuthenticated(req, res, next) {
  if (req.session.userId) {
    next();
  } else {
    res.redirect('/login');
  }
}

// Redirect root to login
app.get('/', (req, res) => {
  res.redirect('/login');
});

// Routes
app.use('/events', isAuthenticated, eventRoutes);
app.use('/', authRoutes);

app.listen(3000, () => {
  console.log('Server is running on port 3000');
});
