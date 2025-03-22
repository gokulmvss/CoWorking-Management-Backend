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
- **Input**:
```json
{
  "firstName": "John",
  "lastName": "Doe Updated",
  "phone": "555-5678",
  "jobTitle": "Senior Software Developer",
  "department": "Engineering"
}
```
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Employee updated successfully",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe Updated",
    "email": "john.doe@example.com",
    "phone": "555-5678",
    "jobTitle": "Senior Software Developer",
    "department": "Engineering",
    "active": true,
    "companyId": 1,
    "companyName": "Tech Innovators Ltd",
    "userId": 1,
    "createdAt": "2025-03-12T10:30:45.123456"
  }
}
```

### 5.5. Get All Employees for a Company
- **Method**: GET
- **URL**: `/api/employees/company/{companyId}`
- **Authentication**: HTTP Basic Auth (COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Employees retrieved successfully",
  "data": [
    {
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
    // ... more employees
  ]
}
```

### 5.6. Get Available Seats for an Employee
- **Method**: GET
- **URL**: `/api/employees/{id}/available-seats`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Available seats retrieved successfully",
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
    // ... more available seats
  ]
}
```

### 5.7. Get Available Workspaces for an Employee
- **Method**: GET
- **URL**: `/api/employees/{id}/available-workspaces`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
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
    // ... more workspaces
  ]
}
```

## 6. Booking APIs

### 6.1. Create a Booking
- **Method**: POST
- **URL**: `/api/bookings`
- **Headers**:
  - Content-Type: application/json
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Input**:
```json
{
  "employeeId": 1,
  "seatId": 2,
  "startTime": "2025-07-15T09:00:00",
  "endTime": "2025-07-15T17:00:00",
  "notes": "Working on project deadline"
}
```
- **Output (201 Created)**:
```json
{
  "success": true,
  "message": "Booking created successfully",
  "data": {
    "id": 1,
    "employeeId": 1,
    "employeeName": "John Doe",
    "seatId": 2,
    "seatNumber": "A002",
    "workspaceId": 1,
    "workspaceName": "Open Space A",
    "bookingDate": "2025-03-12T11:15:30.123456",
    "startTime": "2025-07-15T09:00:00",
    "endTime": "2025-07-15T17:00:00",
    "checkInTime": null,
    "checkOutTime": null,
    "status": "CONFIRMED",
    "notes": "Working on project deadline",
    "createdAt": "2025-03-12T11:15:30.123456"
  }
}
```

### 6.2. Get Booking by ID
- **Method**: GET
- **URL**: `/api/bookings/{id}`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Booking retrieved successfully",
  "data": {
    "id": 1,
    "employeeId": 1,
    "employeeName": "John Doe",
    "seatId": 2,
    "seatNumber": "A002",
    "workspaceId": 1,
    "workspaceName": "Open Space A",
    "bookingDate": "2025-03-12T11:15:30.123456",
    "startTime": "2025-07-15T09:00:00",
    "endTime": "2025-07-15T17:00:00",
    "checkInTime": null,
    "checkOutTime": null,
    "status": "CONFIRMED",
    "notes": "Working on project deadline",
    "createdAt": "2025-03-12T11:15:30.123456"
  }
}
```

### 6.3. Get All Bookings for an Employee
- **Method**: GET
- **URL**: `/api/bookings/employee/{employeeId}`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Bookings retrieved successfully",
  "data": [
    {
      "id": 1,
      "employeeId": 1,
      "employeeName": "John Doe",
      "seatId": 2,
      "seatNumber": "A002",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "bookingDate": "2025-03-12T11:15:30.123456",
      "startTime": "2025-07-15T09:00:00",
      "endTime": "2025-07-15T17:00:00",
      "checkInTime": null,
      "checkOutTime": null,
      "status": "CONFIRMED",
      "notes": "Working on project deadline",
      "createdAt": "2025-03-12T11:15:30.123456"
    }
    // ... more bookings
  ]
}
```

### 6.4. Get Upcoming Bookings for an Employee
- **Method**: GET
- **URL**: `/api/bookings/employee/{employeeId}/upcoming`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Upcoming bookings retrieved successfully",
  "data": [
    {
      "id": 1,
      "employeeId": 1,
      "employeeName": "John Doe",
      "seatId": 2,
      "seatNumber": "A002",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "bookingDate": "2025-03-12T11:15:30.123456",
      "startTime": "2025-07-15T09:00:00",
      "endTime": "2025-07-15T17:00:00",
      "checkInTime": null,
      "checkOutTime": null,
      "status": "CONFIRMED",
      "notes": "Working on project deadline",
      "createdAt": "2025-03-12T11:15:30.123456"
    }
    // ... more upcoming bookings
  ]
}
```

### 6.5. Get Bookings for a Specific Date
- **Method**: GET
- **URL**: `/api/bookings/date/{date}`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Bookings for date retrieved successfully",
  "data": [
    {
      "id": 1,
      "employeeId": 1,
      "employeeName": "John Doe",
      "seatId": 2,
      "seatNumber": "A002",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "bookingDate": "2025-03-12T11:15:30.123456",
      "startTime": "2025-07-15T09:00:00",
      "endTime": "2025-07-15T17:00:00",
      "checkInTime": null,
      "checkOutTime": null,
      "status": "CONFIRMED",
      "notes": "Working on project deadline",
      "createdAt": "2025-03-12T11:15:30.123456"
    }
    // ... more bookings for the date
  ]
}
```

### 6.6. Get Bookings for a Specific Seat
- **Method**: GET
- **URL**: `/api/bookings/seat/{seatId}`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Bookings for seat retrieved successfully",
  "data": [
    {
      "id": 1,
      "employeeId": 1,
      "employeeName": "John Doe",
      "seatId": 2,
      "seatNumber": "A002",
      "workspaceId": 1,
      "workspaceName": "Open Space A",
      "bookingDate": "2025-03-12T11:15:30.123456",
      "startTime": "2025-07-15T09:00:00",
      "endTime": "2025-07-15T17:00:00",
      "checkInTime": null,
      "checkOutTime": null,
      "status": "CONFIRMED",
      "notes": "Working on project deadline",
      "createdAt": "2025-03-12T11:15:30.123456"
    }
    // ... more bookings for the seat
  ]
}
```

### 6.7. Check In for a Booking
- **Method**: PUT
- **URL**: `/api/bookings/{id}/check-in`
- **Authentication**: HTTP Basic Auth (EMPLOYEE role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Check-in successful",
  "data": {
    "id": 1,
    "employeeId": 1,
    "employeeName": "John Doe",
    "seatId": 2,
    "seatNumber": "A002",
    "workspaceId": 1,
    "workspaceName": "Open Space A",
    "bookingDate": "2025-03-12T11:15:30.123456",
    "startTime": "2025-07-15T09:00:00",
    "endTime": "2025-07-15T17:00:00",
    "checkInTime": "2025-07-15T09:05:12.345678",
    "checkOutTime": null,
    "status": "CHECKED_IN",
    "notes": "Working on project deadline",
    "createdAt": "2025-03-12T11:15:30.123456"
  }
}
```

### 6.8. Check Out from a Booking
- **Method**: PUT
- **URL**: `/api/bookings/{id}/check-out`
- **Authentication**: HTTP Basic Auth (EMPLOYEE role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Check-out successful",
  "data": {
    "id": 1,
    "employeeId": 1,
    "employeeName": "John Doe",
    "seatId": 2,
    "seatNumber": "A002",
    "workspaceId": 1,
    "workspaceName": "Open Space A",
    "bookingDate": "2025-03-12T11:15:30.123456",
    "startTime": "2025-07-15T09:00:00",
    "endTime": "2025-07-15T17:00:00",
    "checkInTime": "2025-07-15T09:05:12.345678",
    "checkOutTime": "2025-07-15T17:10:45.678912",
    "status": "CHECKED_OUT",
    "notes": "Working on project deadline",
    "createdAt": "2025-03-12T11:15:30.123456"
  }
}
```

### 6.9. Cancel a Booking
- **Method**: PUT
- **URL**: `/api/bookings/{id}/cancel?employeeId={employeeId}`
- **Authentication**: HTTP Basic Auth (EMPLOYEE or COMPANY_ADMIN role required)
- **Output (200 OK)**:
```json
{
  "success": true,
  "message": "Booking cancelled successfully",
  "data": {
    "id": 1,
    "employeeId": 1,
    "employeeName": "John Doe",
    "seatId": 2,
    "seatNumber": "A002",
    "workspaceId": 1,
    "workspaceName": "Open Space A",
    "bookingDate": "2025-03-12T11:15:30.123456",
    "startTime": "2025-07-15T09:00:00",
    "endTime": "2025-07-15T17:00:00",
    "checkInTime": null,
    "checkOutTime": null,
    "status": "CANCELLED",
    "notes": "Working on project deadline",
    "createdAt": "2025-03-12T11:15:30.123456"
  }
}
```

## 7. Error Responses

For all API endpoints, errors are handled with consistent response structures:

### 7.1. Resource Not Found (404)
```json
{
  "success": false,
  "message": "Resource not found with id: 999",
  "data": null
}
```

### 7.2. Conflict Error (409)
```json
{
  "success": false,
  "message": "Email already exists",
  "data": null
}
```

### 7.3. Validation Error (400)
```json
{
  "success": false,
  "message": "Validation failed for object='createBookingRequest'. Error count: 2",
  "errors": [
    {
      "objectName": "createBookingRequest",
      "field": "startTime",
      "defaultMessage": "Start time must be in the future"
    },
    {
      "objectName": "createBookingRequest",
      "field": "endTime",
      "defaultMessage": "End time must be in the future"
    }
  ],
  "path": "/api/bookings"
}
```

### 7.4. Business Logic Error (400)
```json
{
  "success": false,
  "message": "The seat is already booked for the requested time period",
  "data": null
}
```

### 7.5. Authentication Error (401)
```json
{
  "success": false,
  "message": "Authentication required",
  "data": null
}
```

### 7.6. Authorization Error (403)
```json
{
  "success": false,
  "message": "Access Denied",
  "data": null
}
```
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