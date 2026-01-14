# P3 Manual Test Case Documentation

Each section outlines how a use case can be tested manually, including steps and expected outcomes. The final section includes workflow scenarios that combine multiple use cases.

---

## Use Case 1: View Company Qualifications

**Test Steps:**
1. Launch the app and navigate to the Qualifications page.
2. Cause a delay in loading to see loading aspect.

**Expected Result:**
- Loading Qualifications should be visible while it loads.

**Test Steps:**
1. Launch the app and navigate to the Qualifications page.
2. Wait for data to load from backend.
3. Observe list of qualifications rendered on page.

**Expected Result:**
- A list of all of the qualifications is displayed.
- No qualifications are missing or duplicated no error message is displayed
---

## Use Case 2: View Company Employed Workers

**Test Steps:**
1. Launch the app and server
2. Naviagte to workers page
3. Observe the workers employed by the company


**Expected Result:**
- A list of all the workers employed by the company is displayed
- Each worker's name may be clicked
- No employees are duplicated and no error message is shown.

---

## Use Case 3: View Company Projects

**Test Steps:**
1. Launch the app and the server.
2. Navigate to the projects page.
3. Wait for the data to load from the backend
4. Observe the list of projects rendered on the page.

**Expected Result:**
- A list of all of the company's projects is displayed.
- No projects are missing or duplicated.
- If any error occurs, an error message is shown.

---

## Use Case 4: View Qualification Details

**Test Steps:**
1. Launch the app and navigate to the Qualifications page.
2. Wait for the list of qualifications to load.
3. Click on a qualification from the list.
4. Observe the qualification details that are displayed.
5. Note the workers list and count shown.
6. Click on a worker in the list (if any are present).

**Expected Result:**
- The qualification description is displayed.
- A heading showing "Workers with this qualification (X)" where X is the number of workers is displayed.
- If workers have this qualification, they are listed and clickable.
- If no workers have this qualification, a message stating "No workers currently have this qualification" is displayed.
- Clicking on a worker redirects to that worker's details page.

**Alternative Test Steps:**
1. Launch the app and navigate to the Qualifications page.
2. Select a qualification known to have no workers.

**Expected Result:**
- The qualification description is displayed.
- A heading showing "Workers with this qualification (0)" is displayed.
- A message stating "No workers currently have this qualification" is displayed.

---

## Use Case 5: View Worker Details

**Test Steps:**
1. Launch the app and the server.
2. Navigate to the workers page.
3. Wait for the data to load from the backend.
4. Observe the list of workers rendered on the page.
5. Click on a specific worker from the list. OR Navigate to a project page, click on a project, and click on a worker's name in the project details.

**Expected Result:**
- The worker's detail page should display:
- The worker's name
- The worker's current salary
- The worker's current workload value
- A list of all projects the worker is assigned to
- A list of the worker's qualifications
- Each project in the list should be clickable, opening that project's detail page
- Each qualification in the list should be clickable, opening that qualification's detail page
- If any error occurs during data loading, an error message should be shown
- All data displayed should be accurate and up-to-date with the backend

---

## Use Case 6: View Project Details

**Test Steps:**
1. Launch the app and the server.
2. Navigate to the projects page.
3. Wait for the data to load from the backend
4. Observe the list of projects rendered on the page.
5. Click on a rendered project.

**Expected Result:**
- A list of all of the company's projects is displayed.
- No projects are missing or duplicated.
- If any error occurs, a error message is shown.
- The selected project should display a Size, Status, a list of Workers, a list of all Current Qualifications, and a list of all Missing Qualifications
- Each qualification and worker should be clickable, opening up their specific pages
---

## Use Case 7: Create New Qualification

## Test Steps:
1. Launch the app and server.
2. Navigate to the Qualifications page.
3. Click "Add New Qualification" button.
4. Verify form appears with description input field.
5. Try submitting with empty description to verify validation error appears.
6. Enter a valid qualification description (e.g., "TypeScript").
7. Click "Create Qualification" button.
8. Verify success message appears.
9. Verify qualification list refreshes and includes the new qualification.
10. Click on the new qualification to verify it displays correctly with zero workers.

## Expected Result:
- Form toggles correctly when "Add New Qualification" button is clicked
- Validation prevents creation with empty description
- New qualification is created and appears in the list
- Success/error messages are properly displayed
- Clicking on the new qualification shows proper details including zero workers


---

## Use Case 8: Create New Worker
**Test Steps:**
1. Navigate to Workers page and verify the "Add New Worker" button is visible.
2. Click "Add New Worker" button and confirm form appears with name, salary, and qualifications fields.
3. Try submitting the form with empty fields to verify validation errors appear.
4. Enter negative salary and verify appropriate error message.
5. Enter valid name and salary, then select some qualifications.
6. Click "Create Worker" button and verify success message appears.
7. Confirm the worker list refreshes and includes the newly created worker.
8. Click "Add New Worker" again and verify form resets with empty fields.
9. Enter some data then click "Cancel" and verify form disappears without creating a worker.
10. Simulate server error during submission and verify appropriate error handling.

**Expected Results:**
- Form toggles correctly when "Add New Worker" button is clicked
- Validation prevents creation with invalid data (empty name, invalid salary)
- Worker creation succeeds with proper data and updates the worker list
- Cancellation works without creating workers
- Error states are properly handled and communicated to the user

---

## Use Case 9: Create New Project

1. Launch the app and server.
2. Navigate to the Projects page.
3. Click "Add New Project" button.
4. Enter a valid project name in the name field.
5. Select a project size from the dropdown.
6. Select one or more qualifications from the list.
7. Click "Create Project" button.
8. Verify the project appears in the project list.
9. Verify the project has PLANNED status.
10. Verify the project has the selected qualifications.

## Expected Result:
- New project is created with provided name, size and qualifications.
- Project list is refreshed to show the new project.
- Project is created with PLANNED status.
- If form validation fails, appropriate error messages are displayed.
- Form closes after successful creation.

---

## Use Case 10: Assign Worker

**Test Steps:**
1. Launch the app and the server.
2. Navigate to the Projects page.
3. Click on a project to expand details.
4. Click the "Assign Worker to Project" button.

**Expected Result:**
- System loads available workers not already assigned and with workload < 12.
- Dropdown appears with worker names, workload, and qualification tags.
- If no workers meet the criteria, alert "No available workers to assign to this project." is shown.

**Test Steps:**
5. Click on a worker from the dropdown.

**Expected Result:**
- A PUT request is sent to `/api/assign` with JSON body `{ project, worker }`.
- If response is "OK":
  - Page reloads.
  - Worker appears under the project’s worker list.
- If response is "KO":
  - System shows alert: "Failed to assign worker: KO"
- If there's an error (e.g., network failure):
  - System shows alert: "Error: [message]"

---

## Use Case 11: Unassign Worker

**Test Steps:**
1. Launch the app and server.
2. Navigate to the Projects page.
3. Click on a project to expand details.
4. Click "Unassign" next to a worker.

**Expected Result:**
- The selected worker disappears from the worker list.
- If the worker is not qualified or already removed, an error or no visible change occurs.

---

## Use Case 12: Start Project

**Test Steps:**
1. Navigate to Project page.
2. Click on a project "Signature Verification System"
3. All qualifications should be met, click on "Start Project"

**Expected Result:** ...
- The start project button will disappear
- The Status will go to ACTIVE

---

## Use Case 13: Finish Project

**Test Steps:**
1. Navigate to Project page.
2. Click on a project "E-commerce Fake Product Reviews Detection System"
3. The status should be ACTIVE, click on "Finish Project"

**Expected Result:** ...
- The finish putton will disappear
- The status will go to FINISH
- All the workers will be taken off the project.
---

## Workflow Test Scenarios

### Workflow 1: Full Project Lifecycle

**Steps:**
1. Create a new project with required qualifications.
2. Create a new worker and assign matching qualifications.
3. Assign the worker to the project.
4. Start the project.
5. Unassign the worker (project enters suspended state).
6. Assign a different qualified worker.
7. Start and finish the project.

**Expected Result:**
- The project moves through the correct state transitions.
- Assigned workers are tracked correctly.
- No errors during state changes.

---

### Workflow 2: Qualification Management

**Steps:**
1. Create a new qualification.
2. Create a worker and assign that qualification.
3. View the qualification detail page.
4. Verify that the new worker appears under the qualification.

**Expected Result:**
- Qualification appears immediately after creation.
- Worker list updates in detail view.
