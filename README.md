# Proyecto 3: Integración AWS con Arquitectura Reactiva

## Descripción

Sistema distribuido que demuestra integración con servicios AWS y arquitectura reactiva:

- **AWS Lambda** para procesamiento serverless
- **DynamoDB** para almacenamiento NoSQL
- **AWS Step Functions** para orquestación de workflows
- **Arquitectura Reactiva** con Spring WebFlux
- **Programación Funcional** y **Streams Reactivos**
- **Event-Driven Architecture** con AWS EventBridge
- **Docker** y **Kubernetes** para despliegue

## Características

- ✅ Integración con AWS Lambda
- ✅ DynamoDB para persistencia NoSQL
- ✅ Step Functions para workflows complejos
- ✅ Spring WebFlux (Programación Reactiva)
- ✅ Event-Driven Architecture
- ✅ Docker y Dockerfile
- ✅ Kubernetes manifests
- ✅ Programación funcional con Reactor

## Arquitectura

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       ▼
┌─────────────┐     ┌─────────────┐
│  WebFlux    │────▶│  DynamoDB   │
│   API       │     │   Service   │
└──────┬──────┘     └─────────────┘
       │
       ▼
┌─────────────┐     ┌─────────────┐
│  Event      │────▶│   Lambda    │
│  Bridge     │     │  Functions  │
└─────────────┘     └──────┬──────┘
                           │
                           ▼
                   ┌─────────────┐
                   │    Step     │
                   │  Functions  │
                   └─────────────┘
```

## Tecnologías

- Spring Boot 3.2.0
- Spring WebFlux (Reactive)
- AWS SDK for Java 2.x
- DynamoDB
- AWS Lambda
- AWS Step Functions
- Project Reactor
- Docker
- Kubernetes

## Requisitos

- Java 17+
- Maven 3.8+
- Docker
- AWS CLI configurado (o LocalStack para desarrollo local)
- kubectl (opcional, para Kubernetes)

## Instalación

```bash
# Clonar el repositorio
git clone <repo-url>
cd proyecto3-aws-reactive

# Compilar
mvn clean install

# Ejecutar con LocalStack (simulación AWS local)
docker-compose up -d

# Ejecutar aplicación
mvn spring-boot:run
```

## Endpoints

### API Reactiva

- `GET /api/products` - Listar productos (reactivo)
- `POST /api/products` - Crear producto
- `GET /api/products/{id}` - Obtener producto
- `POST /api/orders` - Crear pedido (dispara Lambda)
- `GET /api/orders/{id}` - Consultar estado del pedido

## Estructura del Proyecto

```
proyecto3-aws-reactive/
├── src/main/java/com/example/aws/
│   ├── AwsReactiveApplication.java
│   ├── domain/
│   ├── application/
│   ├── infrastructure/
│   │   ├── aws/
│   │   │   ├── dynamodb/
│   │   │   ├── lambda/
│   │   │   └── stepfunctions/
│   │   └── web/
│   └── shared/
├── aws/
│   ├── lambda/
│   └── stepfunctions/
├── k8s/
├── docker-compose.yml
└── README.md
```

## AWS Services Utilizados

- **DynamoDB**: Almacenamiento de productos y pedidos
- **Lambda**: Procesamiento de pedidos
- **Step Functions**: Orquestación de workflows
- **EventBridge**: Eventos entre servicios

