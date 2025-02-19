const express = require('express');
const router = express.Router();
const stripe = require('stripe')('stripe-key');

// Make a payment for the auctioned item
router.post('/payments', async (req, res) => {
    try {
        const { amount, currency, source, description } = req.body;
        const paymentIntent = await stripe.paymentIntents.create({
            amount,
            currency,
            payment_method: source,
            description,
            confirm: true,
        });
        res.status(201).send(paymentIntent);
    } catch (error) {
        res.status(400).send(error);
    }
});

module.exports = router;
