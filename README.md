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
| Runtime | `Java` |
| Build Command | `./mvnw clean package -DskipTests` |
| Start Command | `java -jar target/carrerforge-0.0.1-SNAPSHOT.jar` |

Create a Render PostgreSQL database in the same Render workspace and region as the Web Service. Use its internal database host, port, database name, user, and password to populate the backend variables below. Do not use the public database URL when the Web Service and database share a Render region.

Set `JAVA_VERSION` to `17` if the Render service does not already use Java 17 or newer. Render supplies the `PORT` variable; the application binds to it automatically and falls back to `8080` locally.

Set these Render environment variables. Use the internal Render PostgreSQL hostname and credentials, and keep the password in Render's secret environment storage:

```text
SPRING_PROFILES_ACTIVE=prod
JDBC_DATABASE_URL=jdbc:postgresql://<postgres-host>:5432/<database-name>
DB_USERNAME=<postgres-username>
DB_PASSWORD=<postgres-password>
APP_CORS_ALLOWED_ORIGIN=https://<your-vercel-project>.vercel.app
```

`JDBC_DATABASE_URL` must use the `jdbc:postgresql://` form. Do not commit database credentials or production `.env` files.

## Database and seed behavior

The default profile keeps local file-backed H2 in `./data/carrerforge`. The `prod` profile uses PostgreSQL through the PostgreSQL JDBC driver. Hibernate remains on `ddl-auto=update` for this project so the existing schema can start without a migration tool; review a Flyway or Liquibase migration before handling production schema changes.

Startup seed data is idempotent: courses are reused by name and jobs are reused by title/company, so restarts do not create duplicate seed records.

## Verification

```powershell
cd frontend
npm.cmd run build

cd ..\carrerforge
./mvnw.cmd clean test
```

Deployment is not performed by this repository preparation.