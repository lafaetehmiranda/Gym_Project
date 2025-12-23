---
description: 'Modern backend agent specification for Java 21, Quarkus, microservices, Docker, and clean architecture.'
technologies:
  - Java 21
  - Docker 24+
  - Quarkus 3+
  - Microservices
  - Clean Code
  - DDD
  - Hibernate ORM
  - REST
  - gRPC
  - OpenAPI
  - Reactive Programming
  - Testcontainers
  - Prometheus & Grafana
audience: 'Backend developers and AI agents generating or modifying backend code'
---

## Architecture Principles (Priority 1)

> **Rule #1:** Apply clean architecture. Separate layers: domain, application, infrastructure, and interfaces.  
> **Rule #2:** Entities, value objects, and DTOs must be immutable by default.  
> **Rule #3:** Use DDD patterns for domain modeling. Aggregate roots must encapsulate invariants.  
> **Rule #4:** Avoid circular dependencies. Apply dependency inversion rigorously.  
> **Rule #5:** Microservices must be cohesive, loosely coupled, independently deployable, and versioned.  
> **Rule #6:** Apply reactive principles when appropriate using Mutiny or Project Reactor in Quarkus.  
> **Rule #7:** Each service must expose health, readiness, and metrics endpoints for orchestration.  

## Quarkus & Java 21 (Priority 2)

> **Rule #8:** Use Quarkus reactive or imperative APIs appropriately.  
> **Rule #9:** Use Jakarta EE annotations for JAX-RS (`@Path`, `@GET`, `@POST`) and CDI (`@ApplicationScoped`, `@Inject`).  
> **Rule #10:** Prefer `record` types for immutable DTOs and response objects.  
> **Rule #11:** Use `Optional<T>` for nullable return types.  
> **Rule #12:** Apply `@Transactional` only on service layer methods that modify state.  
> **Rule #13:** Leverage Panache or Hibernate ORM with type-safe queries and DTO projections.  
> **Rule #14:** Apply reactive repositories and reactive messaging for event-driven microservices.  

## Microservices Patterns (Priority 3)

> **Rule #15:** Each service must own its database; avoid shared schemas.  
> **Rule #16:** Use REST or gRPC for inter-service communication; prefer contract-first with OpenAPI or Protobuf.  
> **Rule #17:** Apply circuit breakers, retries, and rate limiting for network reliability.  
> **Rule #18:** Support versioned APIs and backward compatibility.  
> **Rule #19:** Use asynchronous event-driven architecture (Kafka, AMQP) when needed for scalability.  

## Docker & Deployment (Priority 4)

> **Rule #20:** Use multi-stage Docker builds for minimal images.  
> **Rule #21:** Base images must be updated regularly (e.g., `quay.io/quarkus/ubi-quarkus-native-image:22.3`).  
> **Rule #22:** Apply non-root user and security best practices in containers.  
> **Rule #23:** Expose only necessary ports and environment variables.  
> **Rule #24:** Use healthchecks, readiness probes, and resource limits for orchestration.  
> **Rule #25:** Support containerized CI/CD pipelines and automated builds.  

## Code Quality & Clean Code (Priority 5)

> **Rule #26:** All classes, methods, and fields must have clear, descriptive English names.  
> **Rule #27:** Avoid unused imports, dead code, and redundant methods.  
> **Rule #27.1:** All necessary imports must be automatically added to every Java file. No file should be missing required import statements.  
> **Rule #28:** Keep methods small, focused, and free of side effects.  
> **Rule #29:** Private fields must be `final` unless mutability is required.  
> **Rule #30:** Apply constructor injection or CDI `@Inject` for dependencies. Avoid static state.  
> **Rule #31:** Apply explicit exception handling; never swallow exceptions silently.  
> **Rule #32:** No comments; code must be self-explanatory.  
> **Rule #33:** Apply Java 21 features like pattern matching, records, sealed classes, and virtual threads when appropriate.  

## Domain Modeling & DTOs (Priority 6)

> **Rule #34:** Keep DTOs simple, immutable, and focused on data transfer.  
> **Rule #35:** Avoid placing business logic in DTOs.  
> **Rule #36:** Use separate packages for domain models, repositories, services, and controllers.  
> **Rule #37:** Annotate entities with JPA (`@Entity`, `@Table`, `@Id`) or reactive equivalents.  
> **Rule #38:** Value objects must enforce invariants at creation and be immutable.  

## Testing & Quality Assurance (Priority 7)

> **Rule #39:** Write unit tests, integration tests, and contract tests for APIs.  
> **Rule #40:** Use Quarkus Dev Services or Testcontainers for isolated test environments.  
> **Rule #41:** Apply test coverage standards; verify critical paths in microservices.  
> **Rule #42:** Use real integration tests over mocks when feasible; avoid over-mocking.  
> **Rule #43:** Apply continuous testing in CI/CD pipelines.  

## Security & Observability (Priority 8)

> **Rule #44:** Apply JWT or OAuth2 for authentication and authorization.  
> **Rule #45:** Sanitize all inputs at service boundaries and validate all incoming data.  
> **Rule #46:** Log structured events and errors with correlation IDs for tracing.  
> **Rule #47:** Expose metrics compatible with Prometheus and dashboards in Grafana.  
> **Rule #48:** Apply rate limiting, CORS policies, and encryption in transit (TLS 1.3).  

## Final Global Rules (Priority 9)

> **Rule #49 (STRONG, GLOBAL):** All code must be evaluated for performance, maintainability, security, and scalability before merging.  
> **Rule #50 (STRONG, GLOBAL):** Strict adherence to clean architecture, microservices principles, reactive programming, and modern Java 21 features.  
> **Rule #51 (STRONG, GLOBAL):** No comments, no redundant code, all identifiers in English, modular design strictly enforced.  
> **Rule #52 (STRONG, GLOBAL):** Docker images must follow best practices, multi-stage builds, and security guidelines.
