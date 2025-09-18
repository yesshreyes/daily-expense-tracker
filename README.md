# Smart Daily Expense Tracker 📒

## Download the APK

Get the latest APK for **Smart Daily Expense Tracker**:

[![Get APK](https://img.shields.io/badge/Get%20APK-%233A6EA5?style=for-the-badge&logo=android&logoColor=white)](https://github.com/yesshreyes/daily-expense-tracker/releases/download/v0.1.0/app.apk)

---

## App Overview

**Smart Daily Expense Tracker** is a native Android module built with **Kotlin + Jetpack Compose** following a clean **MVVM** pattern.  
This module helps small business owners digitize daily expenses that are often lost on paper or WhatsApp — making cash-flow tracking simple, fast, and intelligent.

Core ideas:
- Fast expense capture (title, amount, category, optional notes, receipt image).
- Real-time daily totals and quick filtering.
- Short-term analytics (7-day mock reports) and easy export (PDF/CSV simulation).

---

## ✅ Feature Checklist

### Primary Screens & Flows
- **Expense Entry Screen**
  - [x] Title (text)
  - [x] Amount (₹) — validated (> 0)
  - [x] Category (mock list: Staff, Travel, Food, Utility)
  - [x] Optional Notes (max 100 chars)
  - [x] Optional Receipt Image (upload or mocked)
  - [x] Submit Button: adds expense, shows Toast, entry animation
  - [x] Real-time **Total Spent Today** displayed at top

- **Expense List Screen**
  - [x] View expenses for Today (default)
  - [ ] Navigate previous dates via calendar or filter
  - [ ] Toggle grouping: by Category or by Time
  - [x] Show total count, total amount

- **Expense Report Screen**
  - [x] Mock report for last 7 days
  - [ ] Daily totals
  - [x] Category-wise totals
  - [x] Bar or line chart (mocked)
  - [x] Export simulation (PDF/CSV)
  - [x] Trigger Share intent (optional)

### State & Data
- [x] ViewModel + StateFlow (or LiveData) per screen
- [x] In-memory repository (default) / optional Room persistence
- [x] Navigation (Jetpack Navigation Compose)

### Bonus / Nice-to-have
- [ ] Theme switcher (Light / Dark)
- [x] Persist data locally (Room / DataStore)
- [x] Add animation on add (entry animation)
- [ ] Duplicate detection (basic heuristic)
- [x] Validation (amount > 0, title non-empty)
- [ ] Offline-first sync (mock)
- [x] Reusable UI components 

---
