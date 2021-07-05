# Multiplayer Protocol

This document describes multiplayer implementation of the sudoku game.
We've considered following candidates for the implementation:
  * Polling
  * WebRTC
  * WebSocket Protocol
  * HTTP 2.0
  * gRPC 

Following sections shortly introduce each of these candidates.

## Polling

HTTP 1.0 protocol does not provide a way to to establish a bidirectional communication, where both parties can exchange
messages independently of one another. The only way a server can send messages to the client is if the client sends
the message first.

This brings us to one possibile implementation for bidirectional communication - polling. Polling involve the client
sending periodic requests to the server, effectively listening to any changes to the resources.

This approach is usually not preferable, especially for multiplayer games. Main reasons are:
  * Waste of bandwith and CPU resources
  * Long delays between polls 

## WebRTC

WebRTC is a peer-to-peer protocol, which enables lower average and maximum latency communication because it users SDP
rather than TCP. We're not interested in P2P multiplayer, so this protocol is out of scope.

## WebSocket Protocol

## gRPC
