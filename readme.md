## API

### Exercises

#### Create

Request:
```
POST /api/v1/exercises

{
    "name": "some name",
    "description": "lalala"
}
```

Response:
```
{
    "id": 1,
    "name": "some name",
    "description": "lalala"
}
```

#### Get all

Request:
```
GET /api/v1/exercises
```

Response:
```
[{
    "id": 1,
    "name": "some name",
    "description": "lalala"
}, {
    "id": 2,
    ...
}]
```

#### Get one

Request:
```
GET /api/v1/exercises/{id}
```

Response:
```
{
    "id": 1,
    "name": "some name",
    "description": "lalala"
}
```

#### Update

Request:
```
POST /api/v1/exercises/{id}

{
    "name": "some other name",
    "description": "description"
}
```

Response:
```
{
    "id": 1,
    "name": "some other name",
    "description": "description"
}
```


#### Delete

Request:
```
DELETE /api/v1/exercises/{id}
```

Response: no content
