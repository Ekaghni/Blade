## Customer

### Create customer
```java

JSONObject customerRequest = new JSONObject();
customerRequest.put("name","Gaurav Kumar");
customerRequest.put("contact","9123456780");
customerRequest.put("email","gaurav.kumar@example.com");
customerRequest.put("fail_existing","0");
customerRequest.put("gstin","29XAbbA4369J1PA");
JSONObject notes = new JSONObject();
notes.put("notes_key_1","Tea, Earl Grey, Hot");
notes.put("notes_key_2","Tea, Earl Grey… decaf.");
customerRequest.put("notes",notes);

Customer customer = instance.customers.create(customerRequest);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| name*          | string      | Name of the customer                        |
| email        | string      | Email of the customer                       |
| contact      | string      | Contact number of the customer              |
| fail_existing | string | If a customer with the same details already exists, the request throws an exception by default. Possible value is `0` or `1`|
| gstin         | string      | Customer's GST number, if available. For example, 29XAbbA4369J1PA  |
| notes         | object      | A key-value pair                            |

**Response:**
```json
{
  "id" : "cust_1Aa00000000004",
  "entity": "customer",
  "name" : "Gaurav Kumar",
  "email" : "gaurav.kumar@example.com",
  "contact" : "9123456780",
  "gstin": "29XAbbA4369J1PA",
  "notes":{
    "notes_key_1":"Tea, Earl Grey, Hot",
    "notes_key_2":"Tea, Earl Grey… decaf."
  },
  "created_at ": 1234567890
}
```

-------------------------------------------------------------------------------------------------------

### Edit customer
```java
String customerId = "cust_1Aa00000000003";

JSONObject customerRequest = new JSONObject();
customerRequest.put("name","Gaurav Kumar");
customerRequest.put("contact","9123456780");
customerRequest.put("email","gaurav.kumar@example.com");

Customer customer = instance.customers.edit(customerId,customerRequest);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| CustomerId* | string      | The id of the customer to be updated  |
| email       | string      | Email of the customer                       |
| name        | string      | Name of the customer                        |
| contact     | string      | Contact number of the customer              |

**Response:**
```json
{
  "id": "cust_1Aa00000000003",
  "entity": "customer",
  "name": "Gaurav Kumar",
  "email": "Gaurav.Kumar@example.com",
  "contact": "9000000000",
  "gstin": null,
  "notes": {
    "notes_key_1": "Tea, Earl Grey, Hot",
    "notes_key_2": "Tea, Earl Grey… decaf."
  },
  "created_at": 1582033731
}
```
-------------------------------------------------------------------------------------------------------

### Fetch all customer
```java
JSONObject params = new JSONObject();
params.put("count","1");

List<Customer> customer = instance.customers.fetchAll(params);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| count | integer   | number of payments to fetch (default: 10)        |
| skip  | integer   | number of payments to be skipped (default: 0)    |

**Response:**
```json
{
  "entity":"collection",
  "count":1,
  "items":[
    {
      "id":"cust_1Aa00000000001",
      "entity":"customer",
      "name":"Gaurav Kumar",
      "email":"gaurav.kumar@example.com",
      "contact":"9876543210",
      "gstin":"29XAbbA4369J1PA",
      "notes":{
        "note_key_1":"September",
        "note_key_2":"Make it so."
      },
      "created_at ":1234567890
    }
  ]
}
```

-------------------------------------------------------------------------------------------------------

### Fetch a customer
```java
String customerId = "cust_1Aa00000000001";

Customer customer = instance.customers.fetch(customerId);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| customerId* | string      | The id of the customer to be fetched  |

**Response:**
```json
{
  "id" : "cust_1Aa00000000001",
  "entity": "customer",
  "name" : "Saurav Kumar",
  "email" : "Saurav.kumar@example.com",
  "contact" : "+919000000000",
  "gstin":"29XAbbA4369J1PA",
  "notes" : [],
  "created_at ": 1234567890
}
```

-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/customers/)**