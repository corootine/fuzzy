'use strict'

const https = require('https');
const fs = require('fs');
const ws = require('ws');
const request = require('request')

const options = {
  key: fs.readFileSync('key.pem'),
  cert: fs.readFileSync('cert.pem')
};

var express = require('express')
var app = express()
app.use(express.json())

let server = https.createServer(options, app);

server.addListener('upgrade', (req, res, head) => console.log('UPGRADE:', req.url));
server.on('error', (err) => console.error(err));
server.listen(25564, () => console.log('HTTPS running on port 25564'));

const wss = new ws.Server({ server });

wss.on('connection', function connection(ws, req) {
	console.log("New connection " + req.socket.remoteAddress)
  ws.on('message', (data) => ws.send('Receive: ' + data));

  
  poll(ws)
});

app.post('/appId', function (req, res) {
  console.log(req.body)
  return res.send("{\"appId\":\"test\"}")
})

async function poll(ws) {
  while(true) {
    await sleep(2000)
    ws.send('Hello')
  }
}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}