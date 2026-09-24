# CareerForge Deployment

CareerForge is a Vite/React frontend and Spring Boot backend. Local development uses Vite on port `5173`, Spring Boot on port `8080`, and file-backed H2. Production uses Vercel for the frontend and Render with PostgreSQL for the backend.

## GitHub setup

Create one GitHub repository for this project and push the repository root so both `frontend/` and `carrerforge/` remain available to the deployment services:

```powershell
git init
git add .
git commit -m "Prepare CareerForge for deployment"
git branch -M main
git remote add origin https://github.com/<github-user>/<repository>.git
git push -u origin main
```

Before pushing, confirm that `.env` files, H2 database files, `node_modules/`, `frontend/dist/`, and Maven `target/` output are ignored. Only placeholder configuration belongs in `frontend/.env.example`; never commit production passwords or database URLs containing credentials.

## Local development

Backend:

```powershell
cd carrerforge
./mvnw.cmd spring-boot:run
```

Frontend:

```powershell
cd frontend
npm install
npm run dev
```

Copy `frontend/.env.example` to `frontend/.env.local` for local development when needed. Vite proxies local `/api` requests to `VITE_DEV_API_PROXY`; production browser requests use `VITE_API_BASE_URL`. Vite exposes only variables prefixed with `VITE_` to browser code.

## Vercel settings

Create a Vercel project from the repository with these settings:

| Setting | Value |
| --- | --- |
| Root Directory | `frontend` |
| Framework Preset | `Vite` |
| Build Command | `npm run build` |
| Output Directory | `dist` |
| Install Command | `npm install` |

Add this production environment variable:

```text
VITE_API_BASE_URL=https://<your-render-service>.onrender.com/api
```

Redeploy after changing the variable because Vite embeds it at build time.

## Render settings

Create a Web Service from the repository with these settings:

| Setting | Value |
| --- | --- |
| Root Directory | `carrerforge` |
| Runtime | `Docker` |
| Dockerfile Path | `Dockerfile` |
| Branch | `main` |

The Dockerfile uses a multi-stage Eclipse Temurin Java 17 build and runtime image. Render builds it from `carrerforge/Dockerfile` and starts the container with the Render-provided `PORT` value, defaulting to `8080` locally.

Create a Render PostgreSQL database in the same Render workspace and region as the Web Service. Use its internal database host, port, database name, user, and password to populate the backend variables below. Do not use the public database URL when the Web Service and database share a Render region.

Render supplies the `PORT` variable. `JAVA_VERSION` is not required for the Docker runtime because the Dockerfile pins Eclipse Temurin 17; it may be set to `17` as metadata if your Render workspace requires it.

Set these Render environment variables. Use the internal Render PostgreSQL hostname and credentials, and keep the password in Render's secret environment storage:

```text
SPRING_PROFILES_ACTIVE=prod
DB_URL=jdbc:postgresql://<postgres-host>:5432/<database-name>
DB_USERNAME=<postgres-username>
DB_PASSWORD=<postgres-password>
APP_CORS_ALLOWED_ORIGIN=https://<your-vercel-project>.vercel.app
```

`DB_URL` must use the `jdbc:postgresql://` form. `JDBC_DATABASE_URL` remains supported as a backward-compatible fallback. Do not commit database credentials or production `.env` files.

## Render Blueprint

The repository root contains `render.yaml`. It defines only the `careerforge-backend` Docker web service and references the existing Render PostgreSQL database `careerforge-db`; it does not create a second database.

Blueprint settings:

- Runtime: Docker
- Branch: `main`
- Region: Singapore
- Root directory: `carrerforge`
- Dockerfile path: `./Dockerfile`
- Database: existing `careerforge-db` through `fromDatabase` references
- `APP_CORS_ALLOWED_ORIGIN`: supply the Vercel frontend URL manually during the initial Blueprint setup (`sync: false`)

After connecting the GitHub repository in Render, create or update the Blueprint from `render.yaml`, confirm that the three database references resolve to `careerforge-db`, and enter the Vercel URL when Render prompts for `APP_CORS_ALLOWED_ORIGIN`. This repository does not deploy automatically.

## Database and seed behavior

The default profile keeps local file-backed H2 in `./data/carrerforge`. The `prod` profile uses PostgreSQL through the PostgreSQL JDBC driver. Hibernate remains on `ddl-auto=update` for this project so the existing schema can start without a migration tool; review a Flyway or Liquibase migration before handling production schema changes.

Startup seed data is idempotent: courses are reused by name and jobs are reused by title/company, so restarts do not create duplicate seed records.

## Verification

```powershell
cd frontend
npm.cmd run build

cd ..\carrerforge
./mvnw.cmd clean test
./mvnw.cmd package -DskipTests
```

Deployment is not performed by this repository preparation.