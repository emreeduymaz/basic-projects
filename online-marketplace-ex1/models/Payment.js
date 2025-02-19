const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const PaymentTransactionSchema = new Schema({
    orderId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Order',
        required: true
    },
    transactionId: {
        type: String,
        required: true
    },
    amount: {
        type: Number,
        required: true
    },
    paymentMethod: {
        type: String,
        required: true
    },
    date: {
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('PaymentTransaction', PaymentTransactionSchema);
