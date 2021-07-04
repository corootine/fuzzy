# Matchmaking

This document describes the process of matchmaking two players. The server knows no notion of permanent user ids. All
identification is temporary and there is no way to distinguish users outside their session.

Based on this, we can establish certain rules for matchmaking:

* Users match with each other based on their IDs.
* Users need to know each other's ID in order to match with each other.
* Both users need to enter the corresponding ID in order to match.

## Why do both users need to enter the ID?

We require this in order to prevent spam requests. User ID is a six-digit number. It is very easy to do an exhaustive
DoS for all numbers in that range. As there is no notion of account, and we recycle IDs, user cannot block incoming
requests from a specific ID.

There are certain steps we can take to mitigate this issue. For example, we could let the user manually allow the option
to receive game requests from others.

## Process of matchmaking

Matchmaking establishes a websocket connection between the client and the server. We use this connection
to exchange data during a game session.

See `matchmakng.puml`