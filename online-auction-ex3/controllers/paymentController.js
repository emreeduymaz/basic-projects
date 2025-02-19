const processPayment = require('../utils/payment');

exports.makePayment = async (req, res) => {
    try {
        const paymentResult = await processPayment(req.body);
        res.status(200).send(paymentResult);
    } catch (error) {
        console.error('Error processing payment:', error);
        res.status(400).send({ message: 'Failed to process payment', error });
    }
};
