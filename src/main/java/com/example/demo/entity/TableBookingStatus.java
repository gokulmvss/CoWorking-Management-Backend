package com.example.demo.entity;

public enum TableBookingStatus {
    PENDING,        // Booking is pending approval
    CONFIRMED,      // Booking is confirmed
    CHECKED_IN,     // Employee has checked in
    CHECKED_OUT,    // Employee has checked out
    CANCELLED,      // Booking has been cancelled
    NO_SHOW         // Employee didn't show up
}