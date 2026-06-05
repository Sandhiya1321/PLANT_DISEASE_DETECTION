


<h1 align="center">Plant Leaf Disease Detection Using CNN 🌿</h1>

### About the Project
This project allows users to **inspect the plant leaves**, **detect diseases**, and **store disease reports**. It uses **image processing**, **machine learning (CNN)**, and **Spring Boot + MySQL**. Users can upload leaf images, see the disease detected, and track their detection history.

---



focused in phase 1:
- This project is about plant health disease detection system build using SpringBoot as backend.
- The backend handles image upload, processing, disease detection, and its confidence level.
- Users can upload plant leaf images via a web interface.
- Then the request comes to the backend through RESTAPI in ther controller lay which is used to communicate between the front end and the backend
- Uploaded images are preprocessed for noise removal, shadow reduction, and normalization.
- The backend receives the prediction result from the ML model.
- Results are stored in a MySQL database using JDBC.
- Java’s Object-Oriented Principles (OOPs) are applied for modular and maintainable code.
   - concepts: Inheritance, Encapsulation, Abstraction.
- Spring Boot provides dependency injection, configuration, and request handling.


### Features
- Upload plant leaf images for disease detection  
- Detect diseases using a CNN model  
- Save detection report with:
  - User ID  
  - Leaf image  
  - Disease name  
  - Confidence score  
  - Date & time  
- View all past inspections in the web interface  
- Multi-user support

---

### Tech Stack
- **Backend:** Java, Spring Boot  
- **Database:** MySQL  
- **Frontend:** HTML, CSS, JavaScript  
- **Machine Learning:** CNN (TensorFlow)  

---

### Modules
1. **User Interface** – Upload images and see results  
2. **Image Processing** – Noise removal, shadow reduction, normalization  
3. **Leaf Segmentation** – Isolate leaf from background  
4. **Disease Detection** – CNN predicts disease and confidence score  
5. **Inspection Report Storage** – Store reports in MySQL  
6. **Result Display** – Show results immediately  

---
