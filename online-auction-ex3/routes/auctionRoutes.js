const express = require('express');
const router = express.Router();
const auctionController = require('../controllers/auctionController');

router.post('/', auctionController.addItem);
router.put('/:id', auctionController.editItem);
router.delete('/:id', auctionController.removeItem);
router.get('/:id', auctionController.getItem);
router.get('/', auctionController.getAllItems);

module.exports = router;
