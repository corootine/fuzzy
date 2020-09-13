const https = require('https')
const fs = require('fs')
const port = 25564

const options = {
	key: fs.readFileSync('key.pem'),
	cert: fs.readFileSync('cert.pem')
};

https.createServer(options, function (req, res) {
	res.writeHead(200);
	res.end("hello world!");
}).listen(port)
