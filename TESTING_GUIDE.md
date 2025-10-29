# E-Commerce Admin Dashboard - Testing Guide

This guide provides comprehensive testing procedures for both frontend and backend functionality.

## 🧪 Prerequisites for Testing

Before testing, ensure:
1. ✅ MySQL database is running with the `ecommerce_db` database
2. ✅ Database has sample data (products, orders, users, etc.)
3. ✅ Backend is running on http://localhost:8080/api
4. ✅ Frontend is running on http://localhost:5000

## 📋 Test Checklist

### Backend API Tests

#### 1. Dashboard Endpoints

**Test Dashboard Statistics**
```bash
curl http://localhost:8080/api/admin/dashboard/stats
```
Expected: JSON response with totalRevenue, totalOrders, totalCustomers, etc.

**Test Sales Data**
```bash
curl http://localhost:8080/api/admin/dashboard/sales?days=30
```
Expected: Array of sales data points for last 30 days

#### 2. Order Management Endpoints

**List All Orders**
```bash
curl http://localhost:8080/api/admin/orders
```
Expected: Paginated list of orders

**Get Order by ID**
```bash
curl http://localhost:8080/api/admin/orders/1
```
Expected: Detailed order information

**Update Order Status**
```bash
curl -X PATCH http://localhost:8080/api/admin/orders/1/status \
  -H "Content-Type: application/json" \
  -d '{"status": "shipped"}'
```
Expected: Updated order with new status

**Filter Orders by Status**
```bash
curl "http://localhost:8080/api/admin/orders?status=pending"
```
Expected: Only pending orders

**Search Orders**
```bash
curl "http://localhost:8080/api/admin/orders?search=John"
```
Expected: Orders matching search term

#### 3. Product Management Endpoints

**List All Products**
```bash
curl http://localhost:8080/api/admin/products
```
Expected: List of all products

**Get Product by ID**
```bash
curl http://localhost:8080/api/admin/products/1
```
Expected: Product details

**Create New Product**
```bash
curl -X POST http://localhost:8080/api/admin/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Product",
    "description": "Test Description",
    "price": 99.99,
    "category": "Electronics",
    "stock": 100,
    "sku": "TEST001",
    "isActive": true
  }'
```
Expected: Created product with ID

**Update Product**
```bash
curl -X PUT http://localhost:8080/api/admin/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Product",
    "price": 149.99,
    "stock": 50
  }'
```
Expected: Updated product information

**Delete Product**
```bash
curl -X DELETE http://localhost:8080/api/admin/products/1
```
Expected: Success message

**Filter Products by Category**
```bash
curl "http://localhost:8080/api/admin/products?category=Electronics"
```
Expected: Only electronics products

**Search Products**
```bash
curl "http://localhost:8080/api/admin/products?search=laptop"
```
Expected: Products matching search

**Filter by Stock Status**
```bash
curl "http://localhost:8080/api/admin/products?inStock=true"
```
Expected: Only in-stock products

#### 4. Coupon Management Endpoints

**List All Coupons**
```bash
curl http://localhost:8080/api/admin/coupons
```
Expected: List of all coupons

**Create Coupon**
```bash
curl -X POST http://localhost:8080/api/admin/coupons \
  -H "Content-Type: application/json" \
  -d '{
    "code": "SAVE20",
    "type": "percentage",
    "value": 20,
    "minPurchase": 100,
    "usageLimit": 100,
    "expiresAt": "2025-12-31T23:59:59",
    "isActive": true
  }'
```
Expected: Created coupon

**Update Coupon**
```bash
curl -X PUT http://localhost:8080/api/admin/coupons/1 \
  -H "Content-Type: application/json" \
  -d '{"isActive": false}'
```
Expected: Updated coupon

**Delete Coupon**
```bash
curl -X DELETE http://localhost:8080/api/admin/coupons/1
```
Expected: Success message

#### 5. User Management Endpoints

**List All Users**
```bash
curl http://localhost:8080/api/admin/users
```
Expected: List of customers

**Get User by ID**
```bash
curl http://localhost:8080/api/admin/users/1
```
Expected: User details

**Search Users**
```bash
curl "http://localhost:8080/api/admin/users?search=john@example.com"
```
Expected: Users matching search

**Filter by Status**
```bash
curl "http://localhost:8080/api/admin/users?status=active"
```
Expected: Only active users

#### 6. Notification Endpoints

**List All Notifications**
```bash
curl http://localhost:8080/api/admin/notifications
```
Expected: List of notifications

**Get Unread Notifications**
```bash
curl http://localhost:8080/api/admin/notifications/unread
```
Expected: Only unread notifications

**Get Unread Count**
```bash
curl http://localhost:8080/api/admin/notifications/count
```
Expected: Number of unread notifications

**Mark as Read**
```bash
curl -X PATCH http://localhost:8080/api/admin/notifications/1/read
```
Expected: Notification marked as read

**Mark All as Read**
```bash
curl -X PATCH http://localhost:8080/api/admin/notifications/mark-all-read
```
Expected: All notifications marked as read

### Frontend UI Tests

#### 1. Dashboard Page

**Test:** Open http://localhost:5000
- [ ] Dashboard loads without errors
- [ ] Total Orders displays correct count from database
- [ ] Total Customers displays correct count
- [ ] Stats match backend API response
- [ ] Recent Orders table shows data
- [ ] Sales chart displays (if sales data exists)

#### 2. Orders Page

**Test:** Navigate to Orders page
- [ ] Orders list displays
- [ ] Filter by status works (pending, processing, shipped, etc.)
- [ ] Search by order number or customer name works
- [ ] Click on order opens detail page
- [ ] Order status can be updated
- [ ] Pagination works (if > 10 orders)

**Order Details Page:**
- [ ] Customer information displays
- [ ] Order items list shows
- [ ] Shipping address displays
- [ ] Order timeline/status history shows
- [ ] Status update button works
- [ ] Refund modal (if implemented)

#### 3. Products Page

**Test:** Navigate to Products page
- [ ] Products list displays with images
- [ ] Filter by category works
- [ ] Search by product name works
- [ ] Stock status filter works (in stock / out of stock)
- [ ] "Add Product" button works
- [ ] Edit product button works
- [ ] Delete product confirmation works
- [ ] Product form validation works

**Product Form:**
- [ ] All fields are editable
- [ ] SKU validation (must be unique)
- [ ] Price validation (must be positive)
- [ ] Stock validation (must be number)
- [ ] Category dropdown works
- [ ] Image upload/URL input works
- [ ] Save button creates/updates product
- [ ] Cancel button returns to list

#### 4. Inventory Management Page

**Test:** Navigate to Inventory page
- [ ] All products display with stock levels
- [ ] Low stock products highlighted
- [ ] Out of stock products highlighted
- [ ] "Add Stock" button works
- [ ] "Remove Stock" button works
- [ ] Stock adjustment dialog works
- [ ] Quantity validation (no negative stock)
- [ ] Stock updates reflect immediately

#### 5. Customers Page

**Test:** Navigate to Users/Customers page
- [ ] Customer list displays
- [ ] Search by name/email works
- [ ] Filter by status works (active/blocked)
- [ ] Total orders per customer displays
- [ ] Total spent per customer displays
- [ ] Join date displays correctly
- [ ] View customer details works

#### 6. Coupons Page

**Test:** Navigate to Coupons page
- [ ] Coupon list displays
- [ ] "Create Coupon" button works
- [ ] Coupon form validation works
- [ ] Code must be unique
- [ ] Expiry date validation
- [ ] Usage progress bar displays
- [ ] Edit coupon works
- [ ] Delete coupon confirmation works
- [ ] Toggle active/inactive works

#### 7. Notifications Page

**Test:** Navigate to Notifications page (if implemented)
- [ ] Notifications list displays
- [ ] Unread notifications highlighted
- [ ] Click to mark as read works
- [ ] "Mark all as read" button works
- [ ] Notification count updates in header
- [ ] Filter unread/all works

#### 8. Reports Page

**Test:** Navigate to Reports page (if implemented)
- [ ] Sales charts display
- [ ] Revenue metrics show
- [ ] Date range filter works
- [ ] Export functionality works (if implemented)

### Database Integration Tests

#### 1. Verify Data Persistence

**Create a Product:**
1. Create product via UI
2. Check database: `SELECT * FROM products WHERE sku = 'TEST001';`
3. Verify all fields saved correctly

**Update Order Status:**
1. Update order status via UI
2. Check database: `SELECT * FROM orders WHERE id = 1;`
3. Check status history: `SELECT * FROM order_status_history WHERE order_id = 1;`

**Create Coupon:**
1. Create coupon via UI
2. Check database: `SELECT * FROM coupons WHERE code = 'SAVE20';`

#### 2. Verify Relationships

**Order with Items:**
```sql
SELECT o.*, oi.*, p.name 
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id
WHERE o.id = 1;
```
Verify: Order items reference correct products

**User with Addresses:**
```sql
SELECT u.*, a.* 
FROM users u
LEFT JOIN addresses a ON u.id = a.user_id
WHERE u.id = 1;
```
Verify: User addresses are linked

**Product with Images:**
```sql
SELECT p.*, pi.image_url
FROM products p
LEFT JOIN product_images pi ON p.id = pi.product_id
WHERE p.id = 1;
```
Verify: Product images are linked

### Performance Tests

#### 1. Load Testing

**Large Dataset:**
- Test with 1000+ products
- Test with 1000+ orders
- Verify pagination works
- Check API response times (<1 second)

**Concurrent Users:**
- Simulate multiple users accessing dashboard
- Verify no database lock issues
- Check query performance

#### 2. Query Optimization

**Check Slow Queries:**
Look at backend logs for slow queries (Spring Boot logs SQL execution time)

**Database Indexes:**
Verify indexes exist on:
- products.category
- orders.status
- orders.user_id
- users.email
- coupons.code

### Error Handling Tests

#### 1. Backend Error Handling

**Test 404 Not Found:**
```bash
curl http://localhost:8080/api/admin/products/999999
```
Expected: Proper error message

**Test Invalid Data:**
```bash
curl -X POST http://localhost:8080/api/admin/products \
  -H "Content-Type: application/json" \
  -d '{"name": ""}'
```
Expected: Validation error message

#### 2. Frontend Error Handling

**Test Network Error:**
1. Stop backend
2. Try to load dashboard
3. Verify error message displays
4. Start backend
5. Click retry - should work

**Test Invalid Input:**
1. Try to create product with empty name
2. Verify validation error displays
3. Try to set negative price
4. Verify validation error

### Security Tests (For Production)

⚠️ **These tests should be performed before production deployment:**

- [ ] SQL injection attempts fail
- [ ] XSS attacks are prevented
- [ ] CSRF protection enabled
- [ ] Authentication required for all endpoints
- [ ] Authorization checks role permissions
- [ ] Passwords are hashed (bcrypt)
- [ ] Sensitive data not in logs
- [ ] HTTPS enforced
- [ ] Rate limiting prevents abuse

## 🎯 Test Results Template

### Test Run Information
- **Date:** _______________
- **Tester:** _______________
- **Environment:** Local / Staging / Production
- **Backend Version:** _______________
- **Frontend Version:** _______________

### Summary
- Total Tests: _____
- Passed: _____
- Failed: _____
- Skipped: _____

### Failed Tests
| Test Case | Expected Result | Actual Result | Notes |
|-----------|----------------|---------------|-------|
|           |                |               |       |

### Performance Metrics
- Dashboard Load Time: _____ ms
- Order List Load Time: _____ ms
- Product CRUD Average Time: _____ ms
- API Response Time Average: _____ ms

### Recommendations
_List any improvements, bugs found, or optimization suggestions_

## 💡 Automated Testing (Future Enhancement)

For production, consider implementing:
- **Unit Tests:** Jest for React components
- **Integration Tests:** Testing Library for user interactions
- **E2E Tests:** Playwright or Cypress for full workflows
- **API Tests:** JUnit for Spring Boot controllers
- **Load Tests:** JMeter or k6 for performance testing

## 📞 Reporting Issues

When reporting bugs, include:
1. Steps to reproduce
2. Expected vs actual behavior
3. Screenshots (if UI issue)
4. Browser console errors
5. Backend logs
6. Database state (if relevant)

## ✅ Sign-Off

**Tested By:** _______________  
**Date:** _______________  
**Status:** ☐ Pass ☐ Fail  
**Notes:** _______________________________________________
