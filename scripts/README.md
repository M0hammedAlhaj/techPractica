Local helper scripts

push-to-ecr.sh
- Automates building and pushing both backend and frontend images to ECR.

Usage example:

```bash
export AWS_REGION=us-east-1
export AWS_ACCOUNT_ID=123456789012
export BACKEND_ECR_REPOSITORY=123456789012.dkr.ecr.us-east-1.amazonaws.com/techpractica-backend
export FRONTEND_ECR_REPOSITORY=123456789012.dkr.ecr.us-east-1.amazonaws.com/techpractica-frontend
export TAG=$(git rev-parse --short HEAD)

scripts/push-to-ecr.sh
```

Make scripts executable:

```bash
chmod +x scripts/*.sh
```