***Git Workflow for Our Project***

**Before Starting New Work:**

- Switch to your personal branch (e.g., Rupert, Sahil, etc.).
- Pull the latest changes from testdev to your branch:

  git checkout [YourBranchName]
  git pull origin testdev

**Working on Your Tasks:**

- Make changes in your branch.
- Commit regularly to save your progress:

  git add .
  git commit -m "Description of changes"

**Pushing Changes & Pull Requests:**

- Push your changes to your branch on GitHub:

  git push origin [YourBranchName]

- On GitHub, create a pull request to merge your branch into testdev.
- Teammates review the changes. If all looks good, merge the pull request.

**Addressing Merge Conflicts:**

- If there are merge conflicts during the pull request, resolve them locally by pulling the testdev
  branch into your branch and fixing the conflicts.

**Syncing with Team's Changes:**

- Once your changes are merged into testdev, everyone should pull testdev to their respective
  branches to start new work with the most recent codebase:

  git pull origin testdev

**Final Merging:**

- After thorough testing and once all tasks are complete in testdev, create a pull request to merge
  testdev into main.

**Cleanup (Optional):**

- Once all is done and deployed, older branches (like Rupert, Sahil, etc.) can be deleted to keep
  the repository tidy. Double-check before deleting any branch.