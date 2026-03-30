# 🇱🇰 GigLanka: Hyper-Local Micro-Job Marketplace

**GigLanka** is a real-time, location-based e-commerce platform designed to bridge the gap between temporary labor seekers (**Task Owners**) and the student workforce (**Gig Workers**) in Sri Lanka.

---

## 📺 Project Demonstration
Click the image below to watch the full technical walkthrough and system demo on YouTube:

[![GigLanka Demo Video](https://youtu.be/YG-UORLdWyg?si=AR2OpVFqQvJgosM0)](https://www.youtube.com/watch?v=https://youtu.be/YG-UORLdWyg?si=AR2OpVFqQvJgosM0)
> *Replace **YOUR_VIDEO_ID_HERE** with your actual YouTube video ID.*

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
