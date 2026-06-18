# Apache Kafka Multi-Broker Exploration Cluster in KinD

This repository contains a local sandbox environment featuring a **3-broker Apache Kafka cluster** managed by **Zookeeper** running inside a **KinD (Kubernetes in Docker)** cluster.

It also includes two separate **Spring Boot 3.x** applications built with **Java 21**:

* **Message Publisher (Producer)**
* **Message Consumer**

No database is required. Data persists entirely within Kafka, making this setup ideal for prototyping, learning, and exploration.

---

## 🏗️ Architecture Overview

The environment bridges your local machine with Kafka brokers running inside Kubernetes through:

* Dual-listener Kafka configuration
* Kubernetes Services
* Port forwarding
* Local host aliasing

This allows locally running Spring Boot applications to communicate seamlessly with Kafka brokers deployed inside KinD.

---

## 🛠️ Step 1: Deploy Kafka Infrastructure

Create the namespace and deploy the Kafka cluster resources.

```bash
kubectl create namespace kafka

kubectl apply -f kafka-cluster.yaml -n kafka
```

This manifest provisions:

* 1 Zookeeper instance
* 3 Kafka brokers
* Internal and external listeners
* Kubernetes services for broker access

---

## 🌐 Step 2: Configure Local Networking

Since the Spring Boot applications run on your host machine while Kafka runs inside Kubernetes containers, perform the following networking setup before starting the applications.

### 1️⃣ Update Your Local Hosts File

Kafka brokers advertise their broker hostnames back to clients during connection handshakes.

Map those hostnames to your local loopback address (`127.0.0.1`).

#### Windows

Open **Notepad as Administrator** and edit:

```text
C:\Windows\System32\drivers\etc\hosts
```

#### macOS / Linux

```bash
sudo nano /etc/hosts
```

Add the following entry:

```text
127.0.0.1 kafka-broker-1 kafka-broker-2 kafka-broker-3
```

---

### 2️⃣ Start Port Forwarding

Open **three separate terminal windows** and keep them running.

#### Terminal 1

```bash
kubectl port-forward svc/kafka-broker-1 9092:9092 -n kafka
```

#### Terminal 2

```bash
kubectl port-forward svc/kafka-broker-2 9093:9093 -n kafka
```

#### Terminal 3

```bash
kubectl port-forward svc/kafka-broker-3 9094:9094 -n kafka
```

These tunnels expose the Kafka brokers from Kubernetes to your local machine.

---

## 🚀 Running the Applications

Start both Spring Boot applications:

### Producer Application

```bash
KafkaZookeeperProducerApplication
```

### Consumer Application

```bash
KafkaZookeeperConsumerApplication
```

You can run them from:

* IntelliJ IDEA
* Eclipse
* VS Code
* Command line

---

## 🧪 End-to-End Verification

### Step 1

Ensure all three port-forwarding sessions are active.

### Step 2

Start:

* Producer application
* Consumer application

### Step 3

Send a test message.

```bash
curl -X POST "http://localhost:8081/api/messages?msg=ExplorationMessage"
```

### Step 4

Verify the consumer logs.

Expected output:

```text
🎯 [Kafka Consumer Event Triggered] -> Extracted: ExplorationMessage
```

This confirms the complete message flow:

```text
HTTP Request
      ↓
Kafka Producer
      ↓
Kafka Topic
      ↓
Kafka Consumer
      ↓
Application Log
```

---

## 💡 Troubleshooting

### Spring Boot Integration Tests Failing

When building the project using Maven:

```bash
mvn clean install
```

integration tests that rely on Kafka connectivity (for example, `@SpringBootTest`) may fail if:

* Port forwarding is not active
* Kafka brokers are unavailable
* Hosts file configuration is missing

To skip tests during packaging:

```bash
mvn clean install -DskipTests
```

---

## 📦 Technology Stack

| Component    | Version      |
| ------------ | ------------ |
| Java         | 21           |
| Spring Boot  | 3.x          |
| Apache Kafka | Multi-Broker |
| Zookeeper    | Included     |
| Kubernetes   | KinD         |
| Maven        | Build Tool   |

---

## 📚 Learning Objectives

This project demonstrates:

* Multi-broker Kafka cluster setup
* Kafka listener configuration
* Kubernetes service networking
* KinD-based local development
* Spring Boot Kafka Producer implementation
* Spring Boot Kafka Consumer implementation
* End-to-end event-driven communication
