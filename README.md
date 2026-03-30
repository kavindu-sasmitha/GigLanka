# 🇱🇰 GigLanka: Hyper-Local Micro-Job Marketplace

**GigLanka** is a real-time, location-based e-commerce platform designed to bridge the gap between temporary labor seekers (**Task Owners**) and the student workforce (**Gig Workers**) in Sri Lanka.

---

# 📺 Project Demonstration (Video)

Click the image below to watch the full system walkthrough and technical explanation of GigLanka:

[![GigLanka Demo](https://img.youtube.com/vi/f4R4Lx3AIug/maxresdefault.jpg)](https://www.youtube.com/watch?v=f4R4Lx3AIug)

> link is dons't support , [click this](https://www.youtube.com/watch?v=f4R4Lx3AIug) watch video.

---
## 🚀 Key Features

* **Flash Match Confirmation:** First-come, first-served task assignment to ensure speed and efficiency.
* **Hyper-Local Alerts:** Utilizing geospatial coordinates to notify students within a specific radius of a job.
* **Escrow-Protected Payments:** Funds are held securely until the task is verified as complete.
* **Automated Revenue Engine:** Built-in logic for a **2% system commission** and **98% student payout**.
* **Verified Digital Identity:** Secure onboarding using NIC and University ID verification.

---

## 🛠️ Technical Stack

### **Backend**
* **Language:** Java 17+
* **Framework:** Spring Boot (REST APIs, Spring Security)
* **Database:** MySQL (Relational data & Transaction management)
* **Security:** JWT for stateless authentication.

### **Frontend**
* **Languages:** HTML5, CSS3, JavaScript
* **Styling:** Bootstrap / Tailwind CSS
* **Design:** Fully mobile-responsive UI for on-the-go students.

---

## 📂 Project Structure

```text
src/main/java/edu/lk/ijse/back_end/
├── config/      # Security & App Configurations
├── controller/  # REST Endpoints
├── dto/         # Data Transfer Objects
├── entity/      # Database Entities (User, Job, Transaction)
├── repo/        # Spring Data Repositories
├── service/     # Core Business Logic (Commission calculations)
└── util/        # Helper classes
