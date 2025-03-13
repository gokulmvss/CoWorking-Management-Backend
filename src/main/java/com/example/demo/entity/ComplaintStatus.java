package com.example.demo.entity;

public enum ComplaintStatus {
    OPEN,           // Complaint has been submitted but not yet addressed
    IN_PROGRESS,    // Complaint is being addressed
    RESOLVED,       // Complaint has been resolved
    CLOSED          // Complaint has been closed without resolution
}
