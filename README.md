# Promo Code application 
A Spring Boot application to manage discount codes for sales or promotions (a.k.a promo codes).

## API Endpoints
### Product (/api/product)
1. Create a new product 

URL: **[POST]** 
```
localhost:8080/api/product/create
```
Example body:
```
{
    "name": "Product",
    "description": "Example product",
    "price": 15.99,
    "currency": "USD"
}
```
2. Get all products

URL: **[GET]** 
```
localhost:8080/api/product/getAll
```
3. Update product data 

URL: **[PATCH]** 
```
localhost:8080/api/product/update/{id}
```
Example body (can take one or more fields):
```
{
    "name": "Updated name"
}
```
### PromoCode (/api/promo-code)
4. Create a new promo code. 

URL: **[POST]** 
```
localhost:8080/api/promo-code/create
```
Example body (for fixed discount):
```
{
    "code": "CODE",
    "expirationDate": "01.01.2025",
    "maxUsages": 2024,
    "discountAmount": 20,
    "currency": "USD",
    "type": "FIXED"
}
```
Example body (for percentage discount):
```
{
    "code": "CODE2",
    "expirationDate": "01.01.2025",
    "maxUsages": 2024,
    "discountAmount": 20,
    "currency": "USD",
    "type": "PERCENTAGE"
}
```
5. Get all promo codes. 

URL: **[GET]** 
```
localhost:8080/api/promo-code/getAll
```
6. Get one promo code's details by providing the promo code. 

URL: **[GET]** 
```
localhost:8080/api/promo-code/{code}
```
Filled URL:
```
localhost:8080/api/promo-code/1
```

### Discount (/api/discount)
7. Get the discount price by providing a product and a promo code.

URL: **[GET]** 
```
localhost:8080/api/discount/calculate
```
Parameters:
```
productId - the ID of the given Product [required]
promoCode - the promo code given in text [required]
```
Filled example:
```
localhost:8080/api/discount/calculate?productId=1&promoCode=CODE
```

### Purchase (/api/purchase)
8. Simulate purchase

URL: **[POST]**
```
localhost:8080/api/purchase/buy
```
Parameters:
```
productId - the ID of the given Product [required]
promoCode - the promo code given in text [optional]
```
Filled example:
```
localhost:8080/api/purchase/buy?productId=1&promoCode=CODE
```
9. A sales report: number of purchases and total value by currency **[GET]**

URL: **[GET]** 
```
localhost:8080/api/purchase/report
```

Sales report look like this:
```
[
    {
        "currency": "GBP",
        "totalAmount": 15.99,
        "totalDiscount": 0.00,
        "purchaseAmount": 1
    },
    {
        "currency": "USD",
        "totalAmount": 47.97,
        "totalDiscount": 15.99,
        "purchaseAmount": 3
    }
]
```
