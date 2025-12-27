# Deploying techPractica to AWS ECS 🔧

This guide covers the minimum steps and repository files needed to build images, push to Amazon ECR, and deploy to Amazon ECS (Fargate) via GitHub Actions.

## What was added
- `.github/workflows/ecs-deploy.yml` — CI pipeline that builds images, pushes to ECR, renders task definitions, and updates ECS services
- `deployment/backend-taskdef.json` — backend task definition template
- `deployment/frontend-taskdef.json` — frontend task definition template

## Required AWS resources
- Two ECR repositories (backend and frontend)
- An ECS Cluster
- Two ECS Services (one for backend, one for frontend), configured to use Fargate
- (Optional) An Application Load Balancer if you want public HTTP access

## Required GitHub Secrets
Set these in the repository Settings → Secrets and variables → Actions
- `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` — A user that can create/register task definitions and update ECS services
- `AWS_REGION` — e.g., `eu-west-1`
- `ECS_CLUSTER` — the ECS cluster name
- `BACKEND_ECR_REPOSITORY` — full image repo URI, e.g.: `123456789012.dkr.ecr.us-west-2.amazonaws.com/techpractica-backend`
- `FRONTEND_ECR_REPOSITORY` — full image repo URI, e.g.: `123456789012.dkr.ecr.us-west-2.amazonaws.com/techpractica-frontend`
- `BACKEND_ECS_SERVICE` — the backend service name
- `FRONTEND_ECS_SERVICE` — the frontend service name

## Notes & Recommendations
- The workflow pushes images tagged `latest`. For safer deploys, consider tagging by git sha or semver and updating the workflow/task-definition to use the tag.
- The task definition templates include log configuration with `REPLACE_AWS_REGION` for `awslogs-region` — replace that string with your AWS region or add a workflow step that patches the task definition dynamically.
- If you want `tsc -b` to run in CI and fail the pipeline on type errors, add an extra job that runs `yarn build` (which runs `tsc -b && vite build`) or run `yarn tsc -b` as a separate step. Currently Docker build uses `yarn build:prod` for the frontend to avoid failing on unused-variable TS errors.
- Ensure your ECS tasks have the correct environment variables and secrets (use AWS Systems Manager Parameter Store / Secrets Manager if needed).

## Local testing helper (manual push)
You can build and push images locally to ECR (after creating repositories and logging in):

```bash
# Authenticate to ECR (using AWS CLI configured locally)
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com

# Build & tag backend
docker build -f backend/Dockerfile-backend -t $BACKEND_ECR_REPOSITORY:local .
docker push $BACKEND_ECR_REPOSITORY:local

# Build & tag frontend
docker build -f frontend/Dockerfile-frontend -t $FRONTEND_ECR_REPOSITORY:local frontend/
docker push $FRONTEND_ECR_REPOSITORY:local
```

## Need help?
I can also:
- Patch the task definitions to fill `awslogs-region` dynamically in the workflow,
- Add an option to tag images with git SHA and update services only for that tag,
- Or go ahead and fix the TypeScript errors so `tsc -b` will pass in CI (recommended for long term).

