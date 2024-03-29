// Do uruchomienia: node -r esm server.js

import express from 'express';
import cors from 'cors';

// API Configuration
const app = express();
app.use(cors());
app.use(express.json());

// MySQL Configuration
// var mysql = require('mysql');
// var con = mysql.createConnection({
//     host: "remotemysql.com",
//     database: "qewTevNSN7",
//     user: "qewTevNSN7",
//     password: "3pNWfu5kSQ"
// });

// con.connect(function (err) {
//     if (err) throw err;
//     console.log("Connected with database!");
// })

// API Functions

var songs = [
    {
        id: 1,
        title: "siema",
        authors: "elo",
        text: "hehehe",
        ytlink: "blabla"
    }
]

app.get('/songs', function (req, res) {
    // res.send("Hello world is working");
    // con.query("SELECT * FROM songs", function (err, result, fields) {
    //     if (err) throw err;
    //     res.send(result);
    // })
    res.send(songs);
});

app.post('/songs', (req, res) => {
    const song = req.body;

    if (typeof song.ytlink !== 'undefined') {
        var sqlQuery = "INSERT INTO songs (id,title,authors,text,ytlink) VALUES (NULL,'" + song.title + "','" + song.authors + "','" + song.text + "','" + song.ytlink + "'); ";
    } else {
        var sqlQuery = "INSERT INTO songs (id,title,authors,text,ytlink) VALUES (NULL,'" + song.title + "','" + song.authors + "','" + song.text + "',NULL); ";
    }


    con.query(sqlQuery, function (err, result) {
        if (err) throw err;
        console.log("1 record inserted");
    });
    res.send(song);
});


// Run API

app.listen(8080, "82.139.172.100", () => {
    console.log("App listening on port 8080");
});