const express = require('express');
const router = express.Router();
const Order = require('../models/Order');

// Place order
router.post('/checkout', async (req, res) => {
    const { address, paymentMethod } = req.body;
    // Get cart items from session or database
    const cart = []; // Placeholder for cart items
    const totalAmount = 0; // Placeholder for total amount

    try {
        const newOrder = new Order({
            userId: req.user.id,
            products: cart,
            totalAmount,
            address,
            paymentMethod
        });
        await newOrder.save();
        res.redirect('/orders');
    } catch (err) {
        res.status(500).send('Server Error');
    }
});

// View order history
router.get('/', async (req, res) => {
    try {
        const orders = await Order.find({ userId: req.user.id });
        res.render('orders', { orders });
    } catch (err) {
        res.status(500).send('Server Error');
    }
});

module.exports = router;
