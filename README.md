# 🚇 Metrolink Journey Planner 
*A Java based route planner for Manchester's Metrolink Network*

---

## 📌 Overview
The **MetroLink Journey Planner** is a **Java GUI application** that helps users **plan journeys** across Manchester’s tram network.
It calculates the **shortest travel time** or **minimum line changes** between any two stations using **graph-based routing algorithms**.



With an intuitive interface, the app lets you:

- Select **start** and **destination** stations.
- Choose between **Shortest Path 🕒** or **Fewest Changes 🔄**.
- Handle **Delays** and stations **Closures** interactively.
- Visualise results with a **step-by-step route**.

---

## 🚀 Features
- **Shortest Path Calculation** – Uses Dijkstra’s algorithm for minimum journey time.
- **Fewest Changes Mode** – Optimizes journeys with minimal line transfers.
- **Add Delays & Closures** – Dynamically adjust your route based on real-time disruptions.
- **User-Friendly GUI** – Built with Swing for a smooth experience.
- **Detailed Route Output** – Displays station-by-station instructions with line colours.

---

## 📂 Project Structure
```
├── README.md
├── resources
│   └── Metrolink_times_linecolour.csv # Metrolink network data
└── src
    ├── Driver.java 
    ├── graph  # Graph data structure
    │   ├── Edge.java
    │   ├── Graph.java
    │   └── Node.java
    ├── routing   # Routing algorithms
    │   ├── FewestChangesRoute.java
    │   └── ShortestRoute.java
    ├── screens   # GUI and Screens
    │   ├── CloseScreen.java
    │   ├── DelayScreen.java
    │   └── GUI.java
    └── utils   # CSV readers & helpers
        └── CSVReader.java
```

---

## 🛠️ Installation & Setup

### **1️⃣ Clone the Repository**
```bash
git clone https://github.com/adam-2005-99/MetroLink-Journey-Planner.git
cd MetroLink-Journey-Planner
```
### **2️⃣ Set the CSV File Path**
Before compiling, set the path to your ***Metrolink_times_linecolour.csv*** inside **Driver.java**.


### **3️⃣ Compile the Project**
```bash
cd src
javac *.java */*.java
java -cp .:screens:graph:routing:utils Driver   # for macOS & Linux
java -cp ".;screens;graph;routing;utils" Driver   # for Windows
```

---

## 📚 Usage
### **1. Launch the App**
- Enter **Start Station** and **Destination**.
- Choose **Shortest Path** or **Fewest Changes**.
- Click **Find Path**.

### **2. Handle Delays & Closures**
- Use the **Add Delay** button to simulate longer travel times.
- Use the **Add Closure** button to set a station as closed.

### **3. View Results**
- The route will be displayed **station by station**.
- Shows **line colours** and **total journey time** or **number of changes**.

---
## 🖼️ Screenshots
![GUI](resources/"GUI picture.png") 

![Delay Screen](resources/"Delay screen.png")  

![Closure Screen](resources/"Closure screen.png")



---
## 🧠 Future Enhancements
- Add **real-time data** integration for live tram delays 
- Deploy as a **web app** using JavaFX or Spring Boot
- Interactive **map-based visualisation** of routes

---
## 🧑🏻‍💻 Author
**Name:** Adam Atrash   
**Email:** adam.m.m.atrash@gmail.com


--- 
## 📜 License
This project is licensed under the MIT License — feel free to use and modify.



