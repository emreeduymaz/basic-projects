const express = require('express');
const router = express.Router();
const eventController = require('../controllers/eventController');

router.get('/', eventController.getEvents);
router.get('/new', eventController.showCreateForm);  // Yeni rota
router.post('/', eventController.createEvent);
router.post('/update/:id', eventController.updateEvent);
router.get('/edit/:id', eventController.showEditForm);
router.get('/delete/:id', eventController.deleteEvent);


module.exports = router;
