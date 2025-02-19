const express = require('express');
const router = express.Router();
const Auction = require('../models/Auction');

// Get all auctions
router.get('/auctions', async (req, res) => {
    try {
        const auctions = await Auction.find();
        res.send(auctions);
    } catch (error) {
        res.status(500).send(error);
    }
});

// Add an item to an auction
router.post('/auctions', async (req, res) => {
    const auction = new Auction(req.body);
    try {
        await auction.save();
        res.status(201).send(auction);
    } catch (error) {
        res.status(400).send(error);
    }
});

// Edit an item for bidding
router.put('/auctions/:id', async (req, res) => {
    try {
        const auction = await Auction.findByIdAndUpdate(req.params.id, req.body, { new: true, runValidators: true });
        if (!auction) {
            return res.status(404).send();
        }
        res.send(auction);
    } catch (error) {
        res.status(400).send(error);
    }
});

// Remove an item from the auction
router.delete('/auctions/:id', async (req, res) => {
    try {
        const auction = await Auction.findByIdAndDelete(req.params.id);
        if (!auction) {
            return res.status(404).send();
        }
        res.send(auction);
    } catch (error) {
        res.status(500).send(error);
    }
});

// Download information about the item at the auction
router.get('/auctions/:id', async (req, res) => {
    try {
        const auction = await Auction.findById(req.params.id);
        if (!auction) {
            return res.status(404).send();
        }
        res.send(auction);
    } catch (error) {
        res.status(500).send(error);
    }
});

module.exports = router;
