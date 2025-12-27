# Project README

Welcome to the **techPractica** project! This README will help you get started quickly and point you to important documentation for development guidelines and team workflow.

---

## 📚 Documentation

Please refer to the following detailed guides in the backend documentation folder:

- [Architecture Principles Guide](backend/doc/Architecture_Principles_guide.md)  
- [REST API Development Guidelines](backend/doc/REST_API_Development_Guidelines.md)  
- [Team Workflow & Contribution Guide](/TEAM_WORKFLOW.md)  

---

## 🚀 How to Run the Project

### 1. Always Keep Your Branch Updated

Before starting development or building the project, make sure your branch is up to date:

```bash
# Fetch the latest updates from remote without changing your files
git fetch origin

# Switch to the master branch
git switch master

# Pull the latest changes from the remote master
git pull origin master

# Switch back to your feature branch
git switch your-branch-name

# Merge the updated master into your branch
git merge master

# Resolve conflicts if any, then commit
```

### 2. Running with Docker

The project uses Docker for easy environment setup.

To start the services, run:

```bash
docker-compose up -d
```

This command will start all necessary containers in detached mode.

---

### 3. Next Steps

- Follow the team workflow and coding guidelines in the [Team Workflow Guide](/TEAM_WORKFLOW.md).  
- Check the [REST API Development Guidelines](backend/doc/REST_API_Development_Guidelines.md) before working on API features.  
- Review the [Architecture Principles Guide](backend/doc/Architecture_Principles_guide.md) to understand the system design.

---

Happy coding! 🎉
