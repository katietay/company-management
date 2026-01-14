# P3 Use Case Documentation

This document outlines each use case for our frontend React application. Each use case follows an Actor/System Response format to clearly specify UI behavior and expected backend interaction.

---

### Use Case 1: View Company Qualifications

| Actor Action | System Response |
|--------------|------------------|
| User navigates to the "Qualifications" section of the app | Calls getQualifications()|
|  | During loading "Loading Qualifications" is displayed |
|  | UI displays the list of qualifications using ClickList |
|  | If there's no qualifications, "There are no qualifications!" is displayed |
|  | If there's an error, Error message is displayed |
| User clicks a qualification | Workers are displayed using ClickList |

---

### Use Case 2: View Company Employed Workers

| Actor Action | System Response |
|--------------|------------------|
| User navigates to the "Workers" section of the app | Calls getWorkers() |
| | During loading "Loading workers" is displayed |
| | UI displays the list of workers using the clickList|
| | If there are no workers. "Cannot load workers. Please try again later." is displayed|
| | If there is an error, Error message is displayed. | 
| User clicks a worker | Worker's salary, workload, projects (displayed via click-list), qualifications (displayed via click-list), is shown. |
| User clicks a worker's project | User is redirected to the project page showing the project clicked on. |
| User clicks on worker's qualifications | User is redirected to the qualifications page showing the qualifications clicked on. |

---

### Use Case 3: View Company Projects

| Actor Action | System Response |
|--------------|------------------|
| User clicks on the "Projects" tab on the navbar | Frontend sends a GET requests to '/api/projects' |
| | Backend returns a list of all projects as a JSON |
| | UI displays the list of projects in a readable format |
| User clicks on a project (optional) | Expands the item to show workers assigned to the project |

---

### Use Case 4: View Qualification Details

| Actor Action | System Response |
|--------------|------------------|
| User clicks on a qualification | Qualification's description is shown, underneath the name. |
|   | A heading showing "Workers with this qualification (count)" is displayed |
|   | All workers who have this qualification are shown using ClickList component |
|   | If no workers have this qualification, a message "No workers currently have this qualification" is displayed |
| User clicks on a worker in the list | User is redirected to that worker's details page |

### Use Case 5: View Worker Details

| Actor Action | System Response |
|--------------|------------------|
| User clicks a worker | Worker's salary, workload, projects (displayed via click-list), qualifications(displayed via click-list), is shown. |
| User clicks a worker's project | User is redirected to the project page showing the project clicked on. |
| User clicks on worker's qualifications | User is redirected to the qualifications page showing the qualifications clicked on. |

---

### Use Case 6: View Project Details

| Actor Action | System Response |
|--------------|------------------|
| User clicks a project | Project's Size, Status, a list of Workers, a list of all Current Qualifications, and a list of all Missing Qualifications is shown. |
| User clicks a project's worker | User is redirected to the worker page showing the worker clicked on. |
| User clicks a qualification | User is redirected to the qualifications page showing the qualification clicked on. |

---

### Use Case 7: Create New Qualification

| Actor Action | System Response |
|--------------|-----------------|
| User clicks "Add New Qualification" button | Form for creating a new qualification is displayed. |
| User enters qualification description | Form shows entered information. |
| User clicks "Create Qualification" button | System creates qualification and updates list. |
| User clicks "Cancel" button | Form is hidden, no qualification created. |

---

### Use Case 8: Create New Worker

| Actor Action | System Response |
|--------------|-----------------|
| User clicks "Add New Worker" button | Form for creating a new worker is displayed with fields for name, salary, and qualifications. |
| User enters worker information (name, salary) and selects qualifications | Form shows entered information and selected qualifications. |
| User clicks "Create Worker" button | System creates new worker and displays success message. Worker list is updated to include the new worker. |
| User clicks "Cancel" button | Form is hidden and no worker is created. |
---

### Use Case 9: Create New Project

| Actor Action | System Response |
|--------------|-----------------|
| User clicks "Add New Project" button | Form for creating a new project is displayed. |
| User enters project information and selects qualifications | Form shows entered information. |
| User clicks "Create Project" button | System creates project and updates list. |
| User clicks "Cancel" button | Form is hidden, no project created. |


---

### Use Case 10: Assign Worker

| Actor Action | System Response |
|--------------|------------------|
| User navigates to the Projects page and expands a project | Project details are shown including workers, status, qualifications, and buttons |
| User clicks "Assign Worker to Project" button | UI displays a dropdown of available workers who are not yet assigned and have workload < 12 |
| | Each worker shows matching and/or missing qualifications they would help satisfy |
| | If no workers are available, system shows alert: "No available workers to assign to this project." |
| User clicks on a worker in the dropdown | Frontend sends PUT request to `/api/assign` with payload `{ project: projectName, worker: workerName }` |
| Backend responds with `"OK"` | Page reloads and the worker appears under the list of assigned workers |
| Backend responds with `"KO"` | System shows alert: "Failed to assign worker: KO" |
| Error during request (e.g., network issue) | System shows alert: "Error: [error message]" |

---

### Use Case 11: Unassign Worker

| Actor Action | System Response |
|--------------|------------------|
| User navigates to Projects page and expands a project | Project details shown with worker list |
| User clicks "Unassign" button next to a worker | Sends PUT request to `/api/unassign` with `{ project, worker }` |
| Backend responds "OK" | Worker is removed from project list |
| Backend responds "KO" | Error message or no visible change |

---

### Use Case 12: Start Project

| Actor Action | System Response |
|--------------|------------------|
| User on a project title click-list  | System displays the project information. If the project can be started, all requirements are met, will show a --- Start Project --- button at the top of the project.|
| User on a project title click-list  | System displays the project information. If the project can not be started, system will show no button to start project.|

---

### Use Case 13: Finish Project

| Actor Action | System Response |
|--------------|------------------|
| User clicks on project title click-list | System displays the project information. If project is in an active state, it will show --- Finish Project --- button at the top of the project.|
| User clicks on project title click-list | System displays the project information. If project is not in a active state, the finish project button, will not be shown. | 
| User clicks on Finish Project button| The system will send a command to the finish endpoint.|