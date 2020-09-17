'use strict'

const https = require('https');
const fs = require('fs');
const ws = require('ws');

const options = {
  key: fs.readFileSync('key.pem'),
  cert: fs.readFileSync('cert.pem')
};

let server = https.createServer(options, (req, res) => {
  res.writeHead(200);
  res.end(index);
});

server.addListener('upgrade', (req, res, head) => console.log('UPGRADE:', req.url));
server.on('error', (err) => console.error(err));
server.listen(25564, () => console.log('HTTPS running on port 25564'));

const wss = new ws.Server({ server });
wss.on('connection', function connection(ws, req) {
	console.log("New connection " + req.socket.remoteAddress)

    ws.send('Hello');   
    ws.on('message', (data) => ws.send('Receive: ' + data));
});