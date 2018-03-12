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
