const Auction = require('../models/auctionModel');

exports.addItem = async (req, res) => {
    const auction = new Auction(req.body);
    try {
        await auction.save();
        res.status(201).send(auction);
    } catch (error) {
        console.error('Error adding item:', error);
        res.status(400).send({ message: 'Failed to add item', error });
    }
};

exports.editItem = async (req, res) => {
    try {
        const auction = await Auction.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!auction) {
            return res.status(404).send({ message: 'Item not found' });
        }
        res.send(auction);
    } catch (error) {
        console.error('Error editing item:', error);
        res.status(400).send({ message: 'Failed to edit item', error });
    }
};

exports.removeItem = async (req, res) => {
    try {
        const auction = await Auction.findByIdAndDelete(req.params.id);
        if (!auction) {
            return res.status(404).send({ message: 'Item not found' });
        }
        res.send({ message: 'Item removed successfully', auction });
    } catch (error) {
        console.error('Error removing item:', error);
        res.status(500).send({ message: 'Failed to remove item', error });
    }
};

exports.getItem = async (req, res) => {
    try {
        const auction = await Auction.findById(req.params.id);
        if (!auction) {
            return res.status(404).send({ message: 'Item not found' });
        }
        res.send(auction);
    } catch (error) {
        console.error('Error fetching item:', error);
        res.status(500).send({ message: 'Failed to fetch item', error });
    }
};

exports.getAllItems = async (req, res) => {
    try {
        const auctions = await Auction.find({});
        res.render('auctions', { auctions });
    } catch (error) {
        console.error('Error fetching items:', error);
        res.status(500).send({ message: 'Failed to fetch items', error });
    }
};
