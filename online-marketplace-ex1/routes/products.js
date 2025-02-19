const express = require('express');
const router = express.Router();
const Product = require('../models/Product');

// Get all products
router.get('/', async (req, res) => {
    try {
        const products = await Product.find();
        res.render('products', { products });
    } catch (err) {
        res.status(500).send('Server Error');
    }
});

// Get product details
router.get('/:id', async (req, res) => {
    try {
        const product = await Product.findById(req.params.id);
        res.render('productDetails', { product });
    } catch (err) {
        res.status(500).send('Server Error');
    }
});

module.exports = router;
