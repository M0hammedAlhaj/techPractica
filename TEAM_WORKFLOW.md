# 👥 Team Workflow & Contribution Guide – techPractica

*Updated: June 29, 2025*

This guide explains how the team collaborates with Git and GitHub: from handling issues to making branches, reviewing code, and merging pull requests. It ensures consistency, collaboration, and safety in every contribution.

---

## 🔄 1. Receiving Tasks (Issues)

All new tasks (features, bugs, enhancements) are tracked using GitHub **Issues**.

* Each issue has a title, description, and is assigned to a team member.
* Developers comment if they want to pick up an unassigned task.
* Issues should be clearly written with expected behavior or steps to reproduce (for bugs).

> ✅ Example: Issue #22 — “Add assigment task”

---

## 🌿 2. Creating a New Branch

After receiving or choosing an issue:

```bash
git checkout dev
git pull origin dev
git checkout -b feature/doctor-search
```

> 🔖 Branch naming rules:

* `feature/` for new features
* `bugfix/` for bug fixes
* `hotfix/` for critical/urgent production fixes

---

## 🛠️ 3. Making Code Changes

* Make all related changes in your branch.
* Frequently commit using clear and concise messages:

```bash
git add .
git commit -m "Add doctor search bar to header"
```

---

## 🚀 4. Push Branch to GitHub

Once changes are ready:

```bash
git push origin feature/doctor-search
```

---

## 🔁 5. Open a Pull Request (PR)

On GitHub:

1. Go to the **Pull Requests** tab.
2. Click **"New Pull Request"**.
3. Set:

   * **Base branch**: `dev`
   * **Compare branch**: your feature or bugfix branch
4. Add a descriptive title and summary.
5. Mention the related issue like:

```
Closes #22
```

---

## 👀 6. Review & Approval & Merge Pull Request (by Mohammed)



## 🔄 7. Sync Your Local Dev Branch

After any PR is merged, all developers should sync their `dev` branch:

```bash
git checkout dev
git pull origin dev
```

---

## ✅ Summary: Our Development Flow

```
Issue → Create Branch → Code & Commit → Push → PR → Review → Merge → Sync
```

Happy coding! 🧠✨
