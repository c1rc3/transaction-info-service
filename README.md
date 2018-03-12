# Transaction service
Manage wallet transactions

### Category
Request
```
curl -XGET /categories
```
Response
```json
{
  "data": [
    "Food & Beverage",
    "Entertainment",
    "Education",
    "Transportation",
    "Travel",
    "Investment",
    "Health & Fitness",
    "Shopping",
    "Other"
  ],
  "code": 1,
  "msg": "OK"
}
```

### Transaction
#### Get transactions with address
```
Param:
    - [required] address: String
    - [optional] category: String, default None.
    - [optional] page: Int, default 1.
    - [optional] size: Int, default 10.
    - [optional] sorts: String, sorts fields seperate by 
``` 
Request
```
curl -XGET /transactions?address=0x087ad24e25a24abf04657112e8eee6e365d698e7
```
Response
```json
{
  "code": 1,
  "msg": "OK",
  "data": {
    "total_element": 100,
    "current_page": 1,
    "size": 10,
    "total_page": 10,
    "from": 0,
    "content": [
      {
        "id": "id",
        "category": "Food",
        "from": "0x087ad24e25a24abf04657112e8eee6e365d698e7",
        "to": "0x6f88c11fdd4fa004e5baf03d9372a9dc7ae6ec97",
        "amount": 1,
        "symbol": "ETC",
        "note": "2 cup of coffee at Shin",
        "tx_hash": "0x6a99c3a1de4e8404b128a2c8148c4ba7c4e26ecea61a00dcdb5a4b380faf5107",
        "timestamp": 1520838395437
      }
    ]
  }
}
```

#### Get transaction by id
Request
```
curl -XGET /transactions/0x6a99c3a1de4e8404b128a2c8148c4ba7c4e26ecea61a00dcdb5a4b380faf5107
```
Response
```json
{
  "code": 1,
  "msg": "OK",
  "data": {
    "id": "0x6a99c3a1de4e8404b128a2c8148c4ba7c4e26ecea61a00dcdb5a4b380faf5107",
    "category": "Food",
    "from": "0x087ad24e25a24abf04657112e8eee6e365d698e7",
    "to": "0x6f88c11fdd4fa004e5baf03d9372a9dc7ae6ec97",
    "amount": 1,
    "symbol": "ETC",
    "note": "2 cup of coffee at Shin",
    "tx_hash": "0x6a99c3a1de4e8404b128a2c8148c4ba7c4e26ecea61a00dcdb5a4b380faf5107",
    "timestamp": 1520838395437
  }
}
```

#### Add a transaction
Request
```
curl -XPOST /transactions -d'{
                                 "id": "0x6a99c3a1de4e8404b128a2c8148c4ba7c4e26ecea61a00dcdb5a4b380faf5107",
                                 "category": "Food",
                                 "from": "0x087ad24e25a24abf04657112e8eee6e365d698e7",
                                 "to": "0x6f88c11fdd4fa004e5baf03d9372a9dc7ae6ec97",
                                 "amount": 1,
                                 "symbol": "ETC",
                                 "note": "2 cup of coffee at Shin",
                                 "tx_hash": "0x6a99c3a1de4e8404b128a2c8148c4ba7c4e26ecea61a00dcdb5a4b380faf5107",
                                 "timestamp": 1520838395437
                               }'
```
Response
```json
{
    "code": 1,
    "msg": "OK",
    "data": true
}
```
