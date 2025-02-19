require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const path = require('path');
const config = require('./config');

const app = express();

mongoose.connect(config.dbUri, { useNewUrlParser: true, useUnifiedTopology: true })
    .then(() => console.log('MongoDB connected successfully'))
    .catch(err => console.log('MongoDB connection error:', err));

app.use(bodyParser.json());

// Set up routes
const userRoutes = require('./routes/userRoutes');
const auctionRoutes = require('./routes/auctionRoutes');
const paymentRoutes = require('./routes/paymentRoutes');

app.use('/api/users', userRoutes);
app.use('/api/auctions', auctionRoutes);
app.use('/api/payments', paymentRoutes);

// Set up view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Serve static files
app.use(express.static(path.join(__dirname, 'public')));

// Define a route for the root URL
app.get('/', (req, res) => {
    res.render('index');
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
