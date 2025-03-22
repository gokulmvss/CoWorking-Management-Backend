# Complete API Documentation

## Table of Contents
- [0. Authentication APIs](#0-authentication-apis)
- [1. Coworking Space APIs](#1-coworking-space-apis)
- [2. Workspace APIs](#2-workspace-apis)
- [3. Seat APIs](#3-seat-apis)
- [4. Company APIs](#4-company-apis)
- [5. Employee APIs](#5-employee-apis)
- [6. Booking APIs](#6-booking-apis)
- [7. Error Responses](#7-error-responses)

## 0. Authentication APIs

### 0.1. Public Test Endpoint
- **Method**: GET
- **URL**: `/api/auth/test`
- **Authentication**: None (Public)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Public endpoint working",
  "data": "Success"
}
```

### 0.2. Login
- **Method**: POST
- **URL**: `/api/auth/login`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: None (Public)
- **Input**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Authentication successful",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "user@example.com",
    "roles": ["SPACE_OWNER"],
    "active": true
  }
}
```

### 0.3. Register Space Owner
- **Method**: POST
- **URL**: `/api/auth/register/space-owner`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: None (Public)
- **Input**:
```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@example.com",
  "password": "password123"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Space owner registered successfully",
  "data": {
    "id": 1,
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe@example.com",
    "roles": ["SPACE_OWNER"],
    "active": true
  }
}
```

### 0.4. Register Company
- **Method**: POST
- **URL**: `/api/auth/register/company`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: None (Public)
- **Input**:
```json
{
  "companyName": "Tech Solutions",
  "address": "123 Business Street",
  "email": "contact@techsolutions.com",
  "phone": "555-1234",
  "description": "Software development company",
  "adminFirstName": "John",
  "adminLastName": "Smith",
  "adminEmail": "john.smith@example.com",
  "password": "password123"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Company registered successfully",
  "data": {
    "id": 1,
    "name": "Tech Solutions",
    "address": "123 Business Street",
    "email": "contact@techsolutions.com",
    "phone": "555-1234",
    "description": "Software development company",
    "active": true
  }
}
```

### 0.5. Add Employee
- **Method**: POST
- **URL**: `/api/auth/company/{companyId}/add-employee`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Input**:
```json
{
  "firstName": "Mark",
  "lastName": "Johnson",
  "email": "mark.johnson@example.com",
  "phone": "555-5678",
  "jobTitle": "Developer",
  "department": "Engineering"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Employee added successfully",
  "data": {
    "username": "mark.johnson@techsolutions",
    "password": "SomeGeneratedPassword123!"
  }
}
```

### 0.6. Request Password Reset
- **Method**: POST
- **URL**: `/api/auth/password-reset-request`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: None (Public)
- **Input**:
```json
{
  "email": "user@example.com"
}
```
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Password reset request processed",
  "data": null
}
```

### 0.7. Get Current User
- **Method**: GET
- **URL**: `/api/auth/current-user`
- **Authentication**: HTTP Basic Auth
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Current user retrieved successfully",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "user@example.com",
    "roles": ["SPACE_OWNER"],
    "active": true
  }
}
```

### 0.8. Logout
- **Method**: GET
- **URL**: `/api/auth/logout`
- **Authentication**: HTTP Basic Auth
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Logout successful",
  "data": null
}
```

## 1. Coworking Space APIs

### 1.1. Create a Coworking Space
- **Method**: POST
- **URL**: `/api/coworking-spaces`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Input**:
```json
{
  "name": "Downtown Hub",
  "address": "123 Main Street, City",
  "contactEmail": "contact@downtownhub.com",
  "contactPhone": "555-1234",
  "description": "A modern coworking space in downtown with excellent amenities"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Coworking space created successfully",
  "data": {
    "id": 1,
    "name": "Downtown Hub",
    "address": "123 Main Street, City",
    "contactEmail": "contact@downtownhub.com",
    "contactPhone": "555-1234",
    "description": "A modern coworking space in downtown with excellent amenities",
    "active": true,
    "totalSeats": 0,
    "availableSeats": 0,
    "workspaces": null
  }
}
```

### 1.2. Get All Coworking Spaces
- **Method**: GET
- **URL**: `/api/coworking-spaces`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Coworking spaces retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "Downtown Hub",
      "address": "123 Main Street, City",
      "contactEmail": "contact@downtownhub.com",
      "contactPhone": "555-1234",
      "description": "A modern coworking space in downtown with excellent amenities",
      "active": true,
      "totalSeats": 0,
      "availableSeats": 0,
      "workspaces": null
    }
  ]
}
```

### 1.3. Get Coworking Space by ID
- **Method**: GET
- **URL**: `/api/coworking-spaces/{id}`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Coworking space retrieved successfully",
  "data": {
    "id": 1,
    "name": "Downtown Hub",
    "address": "123 Main Street, City",
    "contactEmail": "contact@downtownhub.com",
    "contactPhone": "555-1234",
    "description": "A modern coworking space in downtown with excellent amenities",
    "active": true,
    "totalSeats": 0,
    "availableSeats": 0,
    "workspaces": null
  }
}
```

### 1.4. Get Coworking Space with Workspaces
- **Method**: GET
- **URL**: `/api/coworking-spaces/{id}/with-workspaces`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Coworking space with workspaces retrieved successfully",
  "data": {
    "id": 1,
    "name": "Downtown Hub",
    "address": "123 Main Street, City",
    "contactEmail": "contact@downtownhub.com",
    "contactPhone": "555-1234",
    "description": "A modern coworking space in downtown with excellent amenities",
    "active": true,
    "totalSeats": 30,
    "availableSeats": 20,
    "workspaces": [
      {
        "id": 1,
        "name": "Open Space A",
        "type": "open space",
        "capacity": 30,
        "location": "Ground Floor",
        "pricePerSeatPerHour": 15.0,
        "available": true,
        "coworkingSpaceName": "Downtown Hub",
        "coworkingSpaceId": 1,
        "totalSeats": 20,
        "availableSeats": 10,
        "companyAllocatedSeats": 10,
        "employeeBookedSeats": 0,
        "seats": null
      }
    ]
  }
}
```

## 2. Workspace APIs

### 2.1. Create a Workspace
- **Method**: POST
- **URL**: `/api/workspaces/coworking-space/{coworkingSpaceId}`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Input**:
```json
{
  "name": "Open Space A",
  "type": "open space",
  "capacity": 30,
  "location": "Ground Floor",
  "pricePerSeatPerHour": 15.00,
  "available": true
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Workspace created successfully",
  "data": {
    "id": 1,
    "name": "Open Space A",
    "type": "open space",
    "capacity": 30,
    "location": "Ground Floor",
    "pricePerSeatPerHour": 15.0,
    "available": true,
    "coworkingSpaceName": "Downtown Hub",
    "coworkingSpaceId": 1,
    "totalSeats": 0,
    "availableSeats": 0,
    "companyAllocatedSeats": 0,
    "employeeBookedSeats": 0,
    "seats": null
  }
}
```

### 2.2. Get All Workspaces for a Coworking Space
- **Method**: GET
- **URL**: `/api/workspaces/coworking-space/{coworkingSpaceId}`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Workspaces retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "Open Space A",
      "type": "open space",
      "capacity": 30,
      "location": "Ground Floor",
      "pricePerSeatPerHour": 15.0,
      "available": true,
      "coworkingSpaceName": "Downtown Hub",
      "coworkingSpaceId": 1,
      "totalSeats": 20,
      "availableSeats": 10,
      "companyAllocatedSeats": 10,
      "employeeBookedSeats": 0,
      "seats": null
    }
  ]
}
```

### 2.3. Get Available Workspaces
- **Method**: GET
- **URL**: `/api/workspaces/coworking-space/{coworkingSpaceId}/available`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Available workspaces retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "Open Space A",
      "type": "open space",
      "capacity": 30,
      "location": "Ground Floor",
      "pricePerSeatPerHour": 15.0,
      "available": true,
      "coworkingSpaceName": "Downtown Hub",
      "coworkingSpaceId": 1,
      "totalSeats": 20,
      "availableSeats": 10,
      "companyAllocatedSeats": 10,
      "employeeBookedSeats": 0,
      "seats": null
    }
  ]
}
```

### 2.4. Get Workspaces with Minimum Available Seats
- **Method**: GET
- **URL**: `/api/workspaces/coworking-space/{coworkingSpaceId}/available-seats?minSeats=5`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Workspaces with available seats retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "Open Space A",
      "type": "open space",
      "capacity": 30,
      "location": "Ground Floor",
      "pricePerSeatPerHour": 15.0,
      "available": true,
      "coworkingSpaceName": "Downtown Hub",
      "coworkingSpaceId": 1,
      "totalSeats": 20,
      "availableSeats": 10,
      "companyAllocatedSeats": 10,
      "employeeBookedSeats": 0,
      "seats": null
    }
  ]
}
```

### 2.5. Get Workspace with Seats
- **Method**: GET
- **URL**: `/api/workspaces/{workspaceId}/with-seats`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Workspace with seats retrieved successfully",
  "data": {
    "id": 1,
    "name": "Open Space A",
    "type": "open space",
    "capacity": 30,
    "location": "Ground Floor",
    "pricePerSeatPerHour": 15.0,
    "available": true,
    "coworkingSpaceName": "Downtown Hub",
    "coworkingSpaceId": 1,
    "totalSeats": 20,
    "availableSeats": 10,
    "companyAllocatedSeats": 10,
    "employeeBookedSeats": 0,
    "seats": [
      {
        "id": 1,
        "seatNumber": "A001",
        "status": "AVAILABLE",
        "type": "Standard Desk",
        "features": "Ergonomic chair",
        "workspaceId": 1,
        "workspaceName": "Open Space A",
        "companyId": null,
        "companyName": null
      },
      {
        "id": 2,
        "seatNumber": "A002",
        "status": "COMPANY_ALLOCATED",
        "type": "Standard Desk",
        "features": "Ergonomic chair",
        "workspaceId": 1,
        "workspaceName": "Open Space A",
        "companyId": 1,
        "companyName": "Tech Innovators Ltd"
      }
    ]
  }
}

### 2.6. Get Workspace by ID

Method: GET
URL: http://localhost:8080/api/workspaces/{workspaceId}
Description: Retrieves a specific workspace by its ID
Authorization: None (Uncomment @PreAuthorize("isAuthenticated()") when implementing security)
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
Request

Path Parameters:

workspaceId (Required): The ID of the workspace to retrieve

Response
200 OK
jsonCopy{
  "success": true,
  "message": "Workspace retrieved successfully",
  "data": {
    "id": 1,
    "name": "Open Space A",
    "type": "open space",
    "capacity": 30,
    "location": "Ground Floor",
    "pricePerSeatPerHour": 15.0,
    "available": true,
    "coworkingSpaceName": "Downtown Hub",
    "coworkingSpaceId": 1,
    "totalSeats": 20,
    "availableSeats": 10,
    "companyAllocatedSeats": 10,
    "employeeBookedSeats": 0,
    "seats": null
  }
}
404 Not Found
jsonCopy{
  "success": false,
  "message": "Workspace not found with id: 999",
  "data": null
}
### 2.7 Delete Workspace by ID

Method: DELETE
URL: http://localhost:8080/api/workspaces/{workspaceId}
Description: Deletes a workspace by its ID. Will fail if workspace has allocated or booked seats.
Authorization: None (Uncomment @PreAuthorize("hasAuthority('ADMIN')") when implementing security)
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
Request

Path Parameters:

workspaceId (Required): The ID of the workspace to delete

Response
200 OK
jsonCopy{
  "success": true,
  "message": "Workspace deleted successfully",
  "data": null
}
404 Not Found
jsonCopy{
  "success": false,
  "message": "Workspace not found with id: 999",
  "data": null
}
400 Bad Request
jsonCopy{
  "success": false,
  "message": "Cannot delete workspace with company-allocated seats. Please deallocate seats first.",
  "data": null
}
jsonCopy{
  "success": false,
  "message": "Cannot delete workspace with employee bookings. Please wait until bookings are completed.",
  "data": null
}
```

## 3. Seat APIs

### 3.1. Add a Single Seat
- **Method**: POST
- **URL**: `/api/seats/workspace/{workspaceId}`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Input**:
```json
{
  "seatNumber": "A001",
  "type": "Standard Desk",
  "features": "Ergonomic chair, adjustable desk height"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Seat created successfully",
  "data": {
    "id": 1,
    "seatNumber": "A001",
    "status": "AVAILABLE",
    "type": "Standard Desk",
    "features": "Ergonomic chair, adjustable desk height",
    "workspaceId": 1,
    "workspaceName": "Open Space A",
    "companyId": null,
    "companyName": null
  }
}
```

### 3.2. Add Multiple Seats (Bulk)
- **Method**: POST
- **URL**: `/api/seats/workspace/{workspaceId}/bulk`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Input**:
```json
{
  "startingNumber": 1,
  "numberOfSeats": 10,
  "type": "Standard Desk",
  "features": "Ergonomic chair, power outlets",
  "prefix": "A"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "10 seats created successfully",
  "data": [
    {
      "id": 1,
      "seatNumber": "A001",
      "status": "AVAILABLE",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": null,
      "companyName": null
    },
    {
      "id": 2,
      "seatNumber": "A002",
      "status": "AVAILABLE",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": null,
      "companyName": null
    }
    // ... more seats
  ]
}
```

### 3.3. Get All Seats in Workspace
- **Method**: GET
- **URL**: `/api/seats/workspace/{workspaceId}`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Seats retrieved successfully",
  "data": [
    {
      "id": 1,
      "seatNumber": "A001",
      "status": "AVAILABLE",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": null,
      "companyName": null
    },
    {
      "id": 2,
      "seatNumber": "A002",
      "status": "COMPANY_ALLOCATED",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": 1,
      "companyName": "Tech Innovators Ltd"
    }
    // ... more seats
  ]
}
```

### 3.4. Get Available Seats in Workspace
- **Method**: GET
- **URL**: `/api/seats/workspace/{workspaceId}/available`
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Available seats retrieved successfully",
  "data": [
    {
      "id": 1,
      "seatNumber": "A001",
      "status": "AVAILABLE",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": null,
      "companyName": null
    }
    // ... more available seats
  ]
}
```

### 3.5. Get Seats by Company
- **Method**: GET
- **URL**: `/api/seats/company/{companyId}`
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Company allocated seats retrieved successfully",
  "data": [
    {
      "id": 2,
      "seatNumber": "A002",
      "status": "COMPANY_ALLOCATED",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": 1,
      "companyName": "Tech Innovators Ltd"
    }
    // ... more allocated seats
  ]
}
```

## 4. Company APIs

### 4.1. Create a Company
- **Method**: POST
- **URL**: `/api/companies`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (SPACE_OWNER role required)
- **Input**:
```json
{
  "name": "Tech Innovators Ltd",
  "address": "456 Business Avenue, City",
  "email": "contact@techinnovators.com",
  "phone": "555-5678",
  "description": "A software development company"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Company created successfully",
  "data": {
    "id": 1,
    "name": "Tech Innovators Ltd",
    "address": "456 Business Avenue, City",
    "email": "contact@techinnovators.com",
    "phone": "555-5678",
    "description": "A software development company",
    "active": true,
    "allocatedSeatsCount": 0,
    "allocatedSeats": null
  }
}
```

### 4.2. Get All Companies
- **Method**: GET
- **URL**: `/api/companies`
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Companies retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "Tech Innovators Ltd",
      "address": "456 Business Avenue, City",
      "email": "contact@techinnovators.com",
      "phone": "555-5678",
      "description": "A software development company",
      "active": true,
      "allocatedSeatsCount": 0,
      "allocatedSeats": null
    }
  ]
}
```

### 4.3. Get Company by ID
- **Method**: GET
- **URL**: `/api/companies/{id}`
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Company retrieved successfully",
  "data": {
    "id": 1,
    "name": "Tech Innovators Ltd",
    "address": "456 Business Avenue, City",
    "email": "contact@techinnovators.com",
    "phone": "555-5678",
    "description": "A software development company",
    "active": true,
    "allocatedSeatsCount": 0,
    "allocatedSeats": null
  }
}
```

### 4.4. Get Company with Allocated Seats
- **Method**: GET
- **URL**: `/api/companies/{id}/with-seats`
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Company with allocated seats retrieved successfully",
  "data": {
    "id": 1,
    "name": "Tech Innovators Ltd",
    "address": "456 Business Avenue, City",
    "email": "contact@techinnovators.com",
    "phone": "555-5678",
    "description": "A software development company",
    "active": true,
    "allocatedSeatsCount": 5,
    "allocatedSeats": [
      {
        "id": 2,
        "seatNumber": "A002",
        "status": "COMPANY_ALLOCATED",
        "type": "Standard Desk",
        "features": "Ergonomic chair, power outlets",
        "workspaceId": 1,
        "workspaceName": "Open Space A",
        "companyId": 1,
        "companyName": "Tech Innovators Ltd"
      }
      // ... more allocated seats
    ]
  }
}
```

### 4.5. Allocate Seats to Company
- **Method**: POST
- **URL**: `/api/companies/{id}/allocate-seats`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Input**:
```json
{
  "workspaceId": 1,
  "numberOfSeats": 5
}
```
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "5 seats allocated to company successfully",
  "data": [
    {
      "id": 2,
      "seatNumber": "A002",
      "status": "COMPANY_ALLOCATED",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": 1,
      "companyName": "Tech Innovators Ltd"
    }
    // ... more allocated seats
  ]
}
```

### 4.6. Release Seats from Company
- **Method**: POST
- **URL**: `/api/companies/{id}/release-seats`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Input**:
```json
[1, 2]
```
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "2 seats released from company successfully",
  "data": [
    {
      "id": 1,
      "seatNumber": "A001",
      "status": "AVAILABLE",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": null,
      "companyName": null
    },
    {
      "id": 2,
      "seatNumber": "A002",
      "status": "AVAILABLE",
      "type": "Standard Desk",
      "features": "Ergonomic chair, power outlets",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "companyId": null,
      "companyName": null
    }
  ]
}
```

## 5. Employee APIs

### 5.1. Create an Employee
- **Method**: POST
- **URL**: `/api/employees/company/{companyId}`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Input**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "555-1234",
  "jobTitle": "Software Developer",
  "department": "Engineering"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Employee created successfully",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "555-1234",
    "jobTitle": "Software Developer",
    "department": "Engineering",
    "active": true,
    "companyId": 1,
    "companyName": "Tech Innovators Ltd",
    "userId": null,
    "createdAt": "2025-03-12T10:30:45.123456"
  }
}
```

### 5.2. Get Employee by ID
- **Method**: GET
- **URL**: `/api/employees/{id}`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Employee retrieved successfully",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "555-1234",
    "jobTitle": "Software Developer",
    "department": "Engineering",
    "active": true,
    "companyId": 1,
    "companyName": "Tech Innovators Ltd",
    "userId": null,
    "createdAt": "2025-03-12T10:30:45.123456"
  }
}
```

### 5.3. Get Current Employee ("Me" Endpoint)
- **Method**: GET
- **URL**: `/api/employees/me`
- **Authentication**: HTTP Basic Auth (EMPLOYEE role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Current employee retrieved successfully",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "555-1234",
    "jobTitle": "Software Developer",
    "department": "Engineering",
    "active": true,
    "companyId": 1,
    "companyName": "Tech Innovators Ltd",
    "userId": 1,
    "createdAt": "2025-03-12T10:30:45.123456"
  }
}
```

### 5.4. Update Employee
- **Method**: PUT
- **URL**: `/api/employees/{id}`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)


1. POST Command (Create Complaint)
HTTP Method: POST Endpoint URL: http://localhost:8080/api/complaints/employee/{employeeId} (Replace {employeeId} with the ID of the employee submitting the complaint.)

Headers:
json
{
    "Content-Type": "application/json"
}
Body (JSON Example):
json
{
    "title": "Broken AC",
    "description": "The AC is not working in the workspace.",
    "workspaceId": 2
}
Expected Response:
Status Code: 201 CREATED

Response Body (Example):

json
{
    "id": 5,
    "title": "Broken AC",
    "description": "The AC is not working in the workspace.",
    "status": "OPEN",
    "submittedBy": {
        "id": 1,
        "firstName": "Kumlu",
        "lastName": "Prakash",
        "email": "kp12@controller.com",
        "phone": "1234567890"
    },
    "workspace": {
        "id": 2,
        "name": "Floor 1",
        "location": "Ground Floor",
        "type": "open space"
    },
    "createdAt": "2025-03-19T16:50:12",
    "updatedAt": "2025-03-19T16:50:12"
}
2. PUT Command (Update Complaint Status)
HTTP Method: PUT Endpoint URL: http://localhost:8080/api/complaints/{id}/status?userId={userId} (Replace {id} with the complaint ID and {userId} with the user ID of the person resolving the complaint.)

Headers:
json
{
    "Content-Type": "application/json"
}
Body (JSON Example):
json
{
    "status": "RESOLVED",
    "resolutionNotes": "The AC has been repaired and is now working fine."
}
Expected Response:
Status Code: 200 OK

Response Body (Example):

json
{
    "id": 5,
    "title": "Broken AC",
    "description": "The AC is not working in the workspace.",
    "status": "RESOLVED",
    "submittedBy": {
        "id": 1,
        "firstName": "Kumlu",
        "lastName": "Prakash",
        "email": "kp12@controller.com",
        "phone": "1234567890"
    },
    "workspace": {
        "id": 2,
        "name": "Floor 1",
        "location": "Ground Floor",
        "type": "open space"
    },
    "createdAt": "2025-03-19T16:50:12",
    "updatedAt": "2025-03-19T17:10:45",
    "resolutionNotes": "The AC has been repaired and is now working fine."
}
Testing Steps in Postman
Open Postman and create a new request for each command.

Use the respective HTTP methods (POST for creation, PUT for updating).

Input the correct URL with path variables ({employeeId}, {id}, {userId}) replaced by actual values.

Add the Content-Type: application/json header.

Add the JSON body under the "Raw" option in the request's body section.

Click "Send" and observe the response.