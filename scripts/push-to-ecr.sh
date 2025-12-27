#!/usr/bin/env bash
set -euo pipefail

# Push built Docker images (backend/frontend) to ECR
# Required env vars:
# - AWS_REGION
# - AWS_ACCOUNT_ID
# - BACKEND_ECR_REPOSITORY -> full repo URI e.g., 123456789012.dkr.ecr.us-east-1.amazonaws.com/techpractica-backend
# - FRONTEND_ECR_REPOSITORY -> full repo URI e.g., 123456789012.dkr.ecr.us-east-1.amazonaws.com/techpractica-frontend
# Optional:
# - TAG (defaults to 'latest')

TAG=${TAG:-latest}

if [[ -z "${AWS_REGION:-}" || -z "${AWS_ACCOUNT_ID:-}" || -z "${BACKEND_ECR_REPOSITORY:-}" || -z "${FRONTEND_ECR_REPOSITORY:-}" ]]; then
  echo "Missing required env vars. See script header for required variables."
  exit 2
fi

echo "Logging into ECR in region ${AWS_REGION}..."
aws ecr get-login-password --region "$AWS_REGION" | docker login --username AWS --password-stdin "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"

echo "Building backend image..."
docker build -f backend/Dockerfile-backend -t "${BACKEND_ECR_REPOSITORY}:${TAG}" ./backend

echo "Pushing backend image to ${BACKEND_ECR_REPOSITORY}:${TAG}..."
docker push "${BACKEND_ECR_REPOSITORY}:${TAG}"

echo "Building frontend image..."
docker build -f frontend/Dockerfile-frontend -t "${FRONTEND_ECR_REPOSITORY}:${TAG}" ./frontend

echo "Pushing frontend image to ${FRONTEND_ECR_REPOSITORY}:${TAG}..."
docker push "${FRONTEND_ECR_REPOSITORY}:${TAG}"

echo "Done. Pushed images with tag: ${TAG}"