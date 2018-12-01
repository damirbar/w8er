const router = require('express').Router();
let Item = require('../schemas/item');
let Session = require('../schemas/session');
let Restaurant = require('../schemas/restaurant');


router.post('/join', function (req, res) {
  Session.findOne({sid: req.body.sid}, function (err, sess) {
    if (err) {
      console.log("error in /join");
      res.status(500).json({message: err});
    }
    else {
      if (sess) {
        if (sess.pass === req.body.pass) {
          sess.updateOne({$push: {members: req.user.id}}, function (err, s) {
            if (err) {
              console.log("error in /join");
              res.status(500).json({message: err});
            }
            else {
              res.status(200).json(sess);
            }
          });
        }
        else {
          res.status(403).json({message: 'incorrect password'});
        }
      }
      else {
        res.status(404).json({message: 'session ' + req.body.sid + ' dose not exist'});
      }
    }
  })
});


// {
//   "restId": "5c017d941b709c5aa81fa2bb",
//   "itemId": "5c0187a37c065c6270836770",
//   "sid": "87321"
// }

router.post('/order-item', function (req, res) {
  Item.findOne({_id: req.body.itemId}, function (err, item) {
    if (err) {
      console.log("error in /order-item");
      res.status(500).json({message: err});
    }
    else {
      if (item) {
        Restaurant.findOne({_id: req.body.restId}, function (err, rest) {
          if (err) {
            console.log("error in /order-item");
            res.status(500).json({message: err});
          }
          else {
            if (rest) {
              if (rest.menu[item.type].includes(item.id)) {
                Session.findOne({sid: req.body.sid}, function (err, sess) {
                  if (err) {
                    console.log("error in /order-item");
                    res.status(500).json({message: err});
                  }
                  else {
                    if (sess) {
                      let i = {
                        itemId: item.id,
                        purchaser: req.user.id
                      };
                      sess.updateOne({$push: {items : i  }},function (err, s) {
                        if (err) {
                          console.log("error in /order-item");
                          res.status(500).json({message: err});
                        }
                        else {
                          res.status(200).json({message: 'added item'});
                        }
                      });
                    }
                    else {
                      res.status(404).json({message: 'session ' + req.body.sid + ' dose not exist'});
                    }
                  }
                });
              }
              else {
                res.status(404).json({message: 'item ' + req.body.itemId + ' not in restaurant'});
              }
            }
            else {
              res.status(404).json({message: 'rest ' + req.body.restId + ' dose not exist'});
            }
          }
        });
      }
      else {
        res.status(404).json({message: 'item ' + req.body.itemId + ' dose not exist'});
      }
    }
  });
});


module.exports = router;





