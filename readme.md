## API

Request and response `Content-Type`s are `application/json` unless stated otherwise.

### Exercises

#### Create

Request:
```
POST /api/v1/exercises

{
    "name": "some name",
    "description": "lalala",
    "images": [{
        "id": "a38b4714-6f16-4cb8-b0c0-b63fe08d80c6"
    }, {
        ...
    )]
}
```

Response:
```
{
    "id": 1,
    "name": "some name",
    "description": "lalala",
    "images": [{
        "id": "a38b4714-6f16-4cb8-b0c0-b63fe08d80c6",
        "name": "snowman.jpg",
        "_links": {
            "self": {
                "href": "http://localhost:8880/api/v1/images/a38b4714-6f16-4cb8-b0c0-b63fe08d80c6"
            }
        }
    }, {
        ...
    }]
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
    "description": "lalala",
    "images": [{
        "id": "a38b4714-6f16-4cb8-b0c0-b63fe08d80c6",
        "name": "snowman.jpg",
        "_links": {
            "self": {
                "href": "http://localhost:8880/api/v1/images/a38b4714-6f16-4cb8-b0c0-b63fe08d80c6"
            }
        }
    }, {
        ...
    }]
}
```

#### Update

Request:
```
POST /api/v1/exercises/{id}

{
    "name": "some other name",
    "description": "description",
    "images": []
}
```

Response:
```
{
    "id": 1,
    "name": "some other name",
    "description": "description",
    "images": []
}
```


#### Delete

Request:
```
DELETE /api/v1/exercises/{id}
```

Response: no content


### Images

#### Upload

Request:
```
POST /api/v1/images
```

Request headers:
```
Content-Type: multipart/form-data
```

Request parameters:
```
file
```

Response:
```
{
    "id": "a38b4714-6f16-4cb8-b0c0-b63fe08d80c6",
    "name": "snowman.jpg",
    "_links": {
        "self": {
            "href": "http://localhost:8880/api/v1/images/a38b4714-6f16-4cb8-b0c0-b63fe08d80c6"
        }
    }
}
```


#### Download

Request:

```
GET http://localhost:8880/api/v1/images/{id}
```

Response headers:
```
Content-Type: image/jpg
Content-Length: 23232
```
