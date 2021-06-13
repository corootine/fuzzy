# Decision Log

### Table of Contents

1. [Client session management](#client-session-management)
2. [Example2](#example2)
3. [Third Example](#third-example)
4. [Fourth Example](#fourth-examplehttpwwwfourthexamplecom)

## Client session management

---
Date: 2021-06-09

### Context

In order to have a valid session, a client must have a valid user ID assigned at any point in time. User ID is a
temporary identifier that the backend gives out to active users. Client needs to keep track of this ID and provide it in
every request in order to identify itself.

The backend guarantees that during a gaming session, the user ID will not change. Based on this, we can conclude that
the client needs to refresh the session only when the application comes to the foreground. If the client
attempts to refresh a valid user ID, the backend will not provide a new one. This way, we guarantee that the client has
a valid user ID whenever the user is in the app.

Based on this information, we've decided that the best approach for ensuring a valid user ID is following:

* Observe on changes in the application lifecycle - foreground and background.
* Everytime the application comes to the foreground, attempt to refresh the user ID.
* Propagate the new user ID to all observers.

## User management

---
Date: 2020-04-25

### Context

We need to decide how to do user management. We currently have two proposals:

1. Persistent storage
2. In-memory storage

The option we want to go with at the moment is temporary, in-memory storage. This greatly simplifies the implementation
as there is no overhead of DB administration and managing user accounts. Users would be assigned temporary IDs from a
pool of IDs. These IDs act as a way to identify users - relevant for matchmaking. When an ID is inactive for a certain
period of time, it gets recycled and sent back to the pool, ready to be assigned to a different user.

This approach naturally has certain drawbacks. While the implementation is relatively simple, the scaling aspect is not
great. We are only able to scale vertically, since storage is tied to a server instance. Due to the same reason,
restoring a game session in case of might be problematic and would require a significant amount of work from the client.

### Decision

We will go with the in-memory storage.

---
---

## Game management

Date: 2020-04-25

### Context

### Decision