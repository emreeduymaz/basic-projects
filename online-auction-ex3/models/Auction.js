const mongoose = require('mongoose');

const AuctionSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    startingBid: {
        type: Number,
        required: true
    },
    currentBid: {
        type: Number,
        default: 0
    },
    endTime: {
        type: Date,
        required: true
    },
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    }
});

module.exports = mongoose.model('Auction', AuctionSchema);
