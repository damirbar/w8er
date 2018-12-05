const router = require('express').Router();
let Item = require('../schemas/item');
let Session = require('../schemas/session');
let Restaurant = require('../schemas/restaurant');

// body =
// {
//   "sid": "67187",
//   "pass": "fkpq"
// };
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


// body =
//   {
//     "restId": "5c017d941b709c5aa81fa2bb",
//     "sid": "67187",
//     "itemId": "5c0187a37c065c6270836770",
//     "notes": "no onions"
//   };

router.post('/add-item-to-cart', function (req, res) {
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
                      let per = {
                        id: req.user.id,
                        first_name: req.user.first_name,
                        last_name: req.user.last_name,
                        notes: req.body.notes
                      };
                      let it = {
                        purchaser: per,
                        name: item.name,
                        description: item.description,
                        type: item.type,
                        price: item.price,
                      };
                      sess.updateOne({$push: {items: it}}, function (err, s) {
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

// body =
//   {
//     "restId": "5c017d941b709c5aa81fa2bb",
//     "sid": "67187"
//   };

router.post('/place-order', function (req, res) {
  Restaurant.findOne({_id: req.body.restId}, function (err, rest) {
    if (err) {
      console.log("error in /order-item");
      res.status(500).json({message: err});
    }
    else {
      if (rest) {
        Session.findOne({sid: req.body.sid}, function (err, sess) {
          if (err) {
            console.log("error in /order-item");
            res.status(500).json({message: err});
          }
          else {
            if (sess) {
              if (sess.items.length !== 0) {
                let order = {
                  table: sess.table,
                  items: sess.items,
                  time: '' + new Date().toISOString().split('T')[0] + '-' + new Date().toISOString().split('T')[1].split('.')[0]
                };
                //send order to restaurant
                console.log(order);
                res.status(200).json({message: 'order placed'});
              }
            }
            else {
              res.status(404).json({message: 'session ' + req.body.sid + ' dose not exist'});
            }
          }
        });
      }
      else {
        res.status(404).json({message: 'rest ' + req.body.restId + ' dose not exist'});
      }
    }
  });
});


module.exports = router;





