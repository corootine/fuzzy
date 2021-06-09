# Decision Log

## User management
Date: 2020-04-25

### Context
We need to decide how to do user management. We currently have two proposals:
1. Persistent storage
2. In-memory storage

The option we want to go with at the moment is temporary, in-memory storage. 
This greatly simplifies the implementation as there is no overhead of DB administration
and managing user accounts. Users would be assigned temporary IDs from a pool of IDs. 
These IDs act as a way to identify users - relevant for matchmaking. When an ID is inactive
for a certain period of time, it gets recycled and sent back to the pool, ready to be assigned
to a different user.

This approach naturally has certain drawbacks. While the implementation is relatively simple,
the scaling aspect is not great. We are only able to scale vertically, since storage is tied
to a server instance. Due to the same reason, restoring a game session in case of  might be problematic
and would require a significant amount of work from the client.

### Decision
We will go with the in-memory storage.

---
---

## Game management
Date: 2020-04-25

### Context


### Decision