# COMPANY METHODS:
**METHOD:** `createProject ()`

*ISP TABLE:*
| **Variable**  | **Partition**                    | **Values**                                 |
|---------------|----------------------------------|----------------------------------------------|
| **name**      | Valid (N1)                       | "Travel Management Application"           |
|               | Null (N2)                        | null                                       |
|               | Empty (N3)                       | " "                                        |
| **qs**        | Valid (Q1)                       | Non-empty set of qualifications in company   |
|               | Null (Q2)                        | null                                      |
|               | Empty (Q3)                       | new HashSet<>()                            |
|               | Not in Company (Q4)              | Contains a qualification not in company    |
| **size**      | Valid (S1)                       | ProjectSize.BIG / MEDIUM / SMALL          |
|               | Null (S2)                        | null                                       |

*BCC TABLE:*
| **Test**                                      | **name** | **qs**  | **size** | **Oracle**                                                                                |
|----------------------------------------------|---------|--------|---------|-------------------------------------------------------------------------------------------|
| **T1 (Base):**       | N1      | Q1     | S1      | Returns a new project with correct name/size; qualifications added; project in company    |
| **T2:**           | N2      | Q1     | S1      | Exception                                                     |
| **T3:**          | N3      | Q1     | S1      | Exception                                                          |
| **T4:** | N1      | Q2     | S1      | Exception                                                          |
| **T5:**| N1      | Q3     | S1      | Exception                                                        |
| **T6:** | N1 | Q4     | S1    | Exception                                                        |
| **T7:**           | N1      | Q1     | S2      | Exception                                                        |
---

*Test Cases Left to Cover:* **None**

**METHOD:** `finish`
*ISP TABLE:*

| Variable  | Characteristics                                                  | Partition | Values                                                       |
|-----------|------------------------------------------------------------------|-----------|--------------------------------------------------------------|
| project   | The project passed to finish()                                   | P0        | null                                                         |
|           |                                                                  | P1        | Non-active project (status = PLANNED or SUSPENDED)             |
|           |                                                                  | P2        | Active project with assigned workers (status = ACTIVE, >0 workers) |
|           |                                                                  | P3        | Active project with no workers (status = ACTIVE, empty worker set)   |

*BCC TABLE:*

| Test Case                                        | Project Partition | Oracle                                                                                         |
|--------------------------------------------------|-------------------|------------------------------------------------------------------------------------------------|
| **T1**        | P0                | Throws IllegalArgumentException                                                                |
| **T2**       | P1                | Project status remains unchanged                                                               |
| **T3**       | P2                | Sets project status to FINISHED; removes all workers; updates assigned/available sets accordingly |
| **T4**       | P3                | Sets project status to FINISHED; no workers to remove                                          |
---
**METHOD:** `UnAssignAll`
*ISP TABLE:*
### ISP Table

| Variable | Characteristics                                           | Partition | Values                                      |
|----------|-----------------------------------------------------------|-----------|---------------------------------------------|
| worker   | The worker passed to unassignAll()                        | W0        | null                                        |
|          |                                                           | W1        | Worker with no projects (empty project set) |
|          |                                                           | W2        | Worker with one or more projects             |
|          |                                                           | W3        | (Optional) Worker not in company's employees   |

*BCC TABLE:*
| Test Case                                           | Worker Partition | Oracle                                                                                      |
|-----------------------------------------------------|------------------|---------------------------------------------------------------------------------------------|
| **T1**       | W0               | Throws IllegalArgumentException                                                             |
| **T2**       | W1               | Leaves worker’s project set empty; removes worker from assigned set                         |
| **T3**       | W2               | Removes worker from all assigned projects; updates company's assigned and available sets      |
| **T4**       | W3               | (Either no effect or throws exception, depending on design)                                  |


**METHOD:** `getUnassignedWorkers()`

*ISP TABLE:*
| **Variable**             | **Partition Label** | **Value**                        |
|--------------------------|---------------------|------------------------------------------|
| Number of Employees      | E0                  | 0 employees (empty company)              |
|                          | E1                  | 2 employees                              |
| Employee Assignment      | A0                  | Unassigned (worker has no projects)      |
|                          | A1                  | Assigned (worker has ≥1 project)         |
**Notes:**
    E0: The company has no employees.
    E1: The company has one or more employees.
    A0: A worker is unassigned if their project set is empty.
    A1: A worker is assigned if they have at least one project.

*BCC TABLE:*
| **Test Case**               | **# Employees** | **Assignment Status**    | **Oracle**                                     |
|-----------------------------|-----------------|--------------------------|------------------------------------------------|
| T1: Initially All Unassigned| E1 (2 workers)  | Both A0                  | Returns both workers (size=2)                  |
| T2: After Partial Assignment| E1 (2 workers)  | One A1 (assigned), one A0  | Returns 1 unassigned worker (only worker unassigned) |
| T3: All Assigned            | E1 (2 workers)  | Both A1                  | Returns an empty set (size=0)                  |
| T4: No Employees            | E0              | N/A                      | Returns an empty set                           |
---

*Test Cases Left to Cover:* **None**

**METHOD:** `getAssignedWorkers()`

*ISP TABLE:*
| **Variable**               | **Partition Label** | **Value**                           |
|----------------------------|---------------------|---------------------------------------------|
| Number of Employees (E)    | E0                  | 0 employees (empty company)                 |
|                            | E1                  | 2 employees                                 |
| Assignment Status (A)      | A0                  | Unassigned (worker has no projects)         |
|                            | A1                  | Assigned (worker has ≥ 1 project)            |

*BCC TABLE:*
| **Test Case**               | **E** | **A** | **Oracle**                                            |
|-----------------------------|-------|-------|-------------------------------------------------------|
| T1: Initially None Assigned | E1    | A0    | Returns empty set (size = 0)                          |
| T2: Some Assigned           | E1    | Mixed (A1 for some, A0 for others) | Returns only the assigned workers (subset of E1)  |
| T3: All Assigned            | E1    | A1    | Returns all employees (size equals number of employees) |
| T4: No Employees            | E0    | N/A   | Returns empty set                                     |
---

**METHOD:** `getProjects()`

*ISP TABLE:*
| **Variable**      | **Partition**       | **Value**                          |
|-------------------|---------------------|---------------------------------------|
| **# of Projects** | **P0**: 0 projects | No projects created                   |
|                   | **P1**: 1 project  | Exactly one project in the company    |
|                   | **P2**: 2+ projects| Multiple distinct projects            |
|                   | **PD**: Duplicates | Two or more projects with same name   |
| **Ordering**      | **O0**: No ordering assumption | Check only membership & size |

*BCC TABLE:*
| **Test Case**                             | **#Projects** | **Ordering** | **Oracle**                                                    |
|------------------------------------------|--------------|--------------|---------------------------------------------------------------|
| **T1**  | P0           | O0           | Returns empty set, size = 0                                  |
| **T2**  | P1           | O0           | Returns set with exactly 1 project                           |
| **T3** | P2     | O0           | Returns set with 2 or more projects                          |
| **T4** | P2     | O0           | Confirms we only check membership & size, not order          |
| **T5** | PD           | O0           | Verifies how duplicates are handled (size may be 1 or 2)     |

*Test Cases Left to Cover:* **None**

**METHOD:** `getQualifications()`

*ISP TABLE:*

| **Variable**     | **Characteristic**                                   | **Partition**  | **Values**                                                           |
|------------------|------------------------------------------------------|---------------|-----------------------------------------------------------------------|
| **#Qual** (Q)    | Number of qualifications in the company’s set        | **Q0**        | 0 qualifications (empty set)                                         |
|                  |                                                      | **Q1**        | 1+ qualifications (non-empty set)                                     |
| **Ordering** (O) | Whether we rely on specific ordering in the returned set | **O0**   | No reliance on ordering (only check membership and size)             |

*BCC TABLE:*

| **Test Cases** | **#Qual** | **Ordering** | **Oracle**                                 |
|---------------|-----------|--------------|--------------------------------------------|
| **T1 (Base)** | Q1        | O0           | 2 qualifications in the set               |
| **T2**        | Q0        | O0           | empty set              |
| **T3**        | Q1        | O0           | 3 qualifications, no ordering assumption  |

---
*Test Cases Left to Cover:* **None**

**METHOD:** `public createQualification()`

*ISP TABLE:*
| **Variable**   | **Partition**   | **Example Value**          |
|----------------|-----------------|----------------------------|
| description    | Valid           | "Project Management"       |
| description    | Null            | null                       |
| description    | Empty           | ""                         |
| description    | Whitespace      | "   "                      |


*BCC TABLE:
| **Test Case**                            | **Partition** | **Oracle**                                                |
|------------------------------------------|---------------|-----------------------------------------------------------|
| testCreateQualification_Valid            | Valid         | Returns a non null Qualification        |
| testCreateQualification_Null             | Null          | Exception                           |
| testCreateQualification_Empty            | Empty         | Exception                           |
| testCreateQualification_Whitespace       | Whitespace    | Exception                           |
---


**METHOD:** `public createWorker()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **available** | The set that contains all currently assigned workers | **A1:** null | null |
| **available** | The set that contains all currently assigned workers | **A2:** Empty set | empty set |
| **available** | The set that contains all currently assigned workers | **A3:** Has workers | 1 worker, 2 workers |
| **employees** | The set that contains all currently unassigned workers | **B1:** null | null |
| **employees** | The set that contains all currently unassigned workers | **B2:** Empty set | empty set|
| **employees** | The set that contains all currently unassigned workers | **B3:** Has workers | 1 worker, 2 workers |
| **qualifications(company)** | The set of all of the company's qualifications | **C1:** null | null |
| **qualifications(company)** | The set of all of the company's qualifications | **C2:** empty set | empty set |
| **qualifications(company)** | The set of all of the company's qualifications | **C3:** Has Qualifications | 1, 2 |
| **name** | The name of the worker | **D1:** null | null |
| **name** | The name of the worker | **D2:** empty | empty string |
| **name** | The name of the worker | **D3:** only whitespace | string with only whitespace |
| **name** | The name of the worker | **D4:** valid string | 1 and 2 char length strings |
| **qualifications(employee)** | The set of qualifications of the worker | **E1:** null | null |
| **qualifications(employee)** | The set of qualifications of the worker | **E2:** no qualifications | empty set |
| **qualifications(employee)** | The set of qualifications of the worker | **E3:** qualifications dont share any with the company | 1 different qualification and 0 shared, 2 different qualification and 0 shared |
| **qualifications(employee)** | The set of qualifications of the worker | **E4:** only some qualifications aren't in the company's | 1 different qualification and 1 shared, 2 different qualifications and 1 shared |
| **qualifications(employee)** | The set of qualifications of the worker | **E5:** all qualifications are in the company's | 1 shared, 2 shared |
| **salary** | Salary of the worker (Negative is invalid, anything else is valid) | **F1:** All real numbers | -1, 0, 1 |

*BCC TABLE:*
| Test Cases | A (available) | B (employees) | C (qualifications(company)) | D (name) | E (qualifications(employee)) | F (salary) |
|------------|---------------|---------------|-----------------------------|----------|------------------------------|------------|
| **T1 (Base)** | Empty Set | Empty Set | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T2** | 2 Workers | Empty Set | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T3** | 1 Worker | Empty Set | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T4(NOT TESTABLE)** | null | Empty Set | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T5** | Empty Set | 1 Workers | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T6** | Empty Set | 2 Workers | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T7(NOT TESTABLE)** | 1 Workers | null | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T8** | Empty Set | Empty Set | 1 Qualification | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T9** | Empty Set | Empty Set | Empty Set | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T10(NOT TESTABLE)** | Empty Set | Empty Set | null | 2 character name | 1 Qualification shared 0 not | 1 salary |
| **T11** | Empty Set | Empty Set | 2 Qualifications | 1 character name | 1 Qualification shared 0 not | 1 salary |
| **T12** | Empty Set | Empty Set | 2 Qualifications | just whitespace | 1 Qualification shared 0 not | 1 salary |
| **T13** | Empty Set | Empty Set | 2 Qualifications | empty string | 1 Qualification shared 0 not | 1 salary |
| **T14** | Empty Set | Empty Set | 2 Qualifications | null | 1 Qualification shared 0 not | 1 salary |
| **T15** | Empty Set | Empty Set | 2 Qualifications | 2 character name | 1 Qualification shared 1 not | 1 salary |
| **T16** | Empty Set | Empty Set | 2 Qualifications | 2 character name | 1 Qualification shared 2 not | 1 salary |
| **T17** | Empty Set | Empty Set | 2 Qualifications | 2 character name | 0 Qualification shared 1 not | 1 salary |
| **T18** | Empty Set | Empty Set | 2 Qualifications | 2 character name | 0 Qualification shared 2 not | 1 salary |
| **T19** | Empty Set | Empty Set | 2 Qualifications | 2 character name | empty set | 1 salary |
| **T20** | Empty Set | Empty Set | 2 Qualifications | 2 character name | null | 1 salary |
| **T21** | Empty Set | Empty Set | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | 0 salary |
| **T22** | Empty Set | Empty Set | 2 Qualifications | 2 character name | 1 Qualification shared 0 not | -1 salary |

*Test Cases Left to cover:* **None!**

**METHOD** `get int hashcode()`

*ISP TABLE*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **hashcode**| the hascode of the class | **A1** default return value| 0 |

*BCC TABLE*
|Test Case| A (hashcode)|
|T1 (Base Case)| 0 |

*Test Cases Left to Cover:* **None**

**METHOD:** `public void start()`

*ISP TABLE:
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Project Status** | Status of the project | **A1:** Null | null |
| **Project Status** | Status of the project | **A2:** Invalid | ACTIVE, FINISHED |
| **Project Status** | Status of the project | **A3:** Invalid | PLANNED, SUSPENDED |
| **Project Qualifications** | How many of these qualifications have been met | **B1:** null | null |
| **Project Qualifications** | How many of these qualifications have been met | **B2:** Empty Set | Empty Set |
| **Project Qualifications** | How many of these qualifications have been met | **B3:** Invalid | 0 met 1 not, 1 met 1 not, 0 met 2 not |
| **Project Qualifications** | How many of these qualifications have been met | **B4:** Valid | All qualifications met |

*BCC Table:*
| Test Cases | A (Project Status) | B (Project Qualifications) |
|------------|------------|
| **T1 (Base)** | PLANNED | Valid |
| **T2** | SUSPENDED | Valid |
| **T3** | ACTIVE | Valid |
| **T4** | FINISHED | Valid |
| **T5(NOT TESTABLE)** | null | Valid |
| **T6** | PLANNED | 0 met 1 not |
| **T7** | PLANNED | 1 met 1 not |
| **T8** | PLANNED | 0 met 2 not |
| **T9** | PLANNED | Empty Set |
| **T10(NOT TESTABLE)** | PLANNED | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public void finish()`

*ISP TABLE:
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Project Status** | Status of the project | **A1:** Null | null |
| **Project Status** | Status of the project | **A2:** Invalid | FINISHED, PLANNED, SUSPENDED |
| **Project Status** | Status of the project | **A3:** Valid | ACTIVE |
| **Project Workers** | Workers assigned to project | **B1:** null | null |
| **Project Workers** | Workers assigned to project | **B3:** Valid | 0, 1, 2 |

*BCC Table:*
| Test Cases | A (Project Status) | B (Project Qualifications) |
|------------|------------|
| **T1 (Base)** | ACTIVE | 2 |
| **T2** | SUSPENDED | 2 |
| **T3** | PLANNED | 2 |
| **T4** | FINISHED | 2 |
| **T5(NOT TESTABLE)** | null | 2 |
| **T6** | PLANNED | 0 |
| **T7** | PLANNED | 1 |
| **T8(NOT TESTABLE)** | PLANNED | null |

*Test Cases Left to Cover:* **All of them!**

**METHOD:** `public void assign()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Project** | Project to assign to | **A1:** Null | null |
| **Project** | Project to assign to | **A2:** Invalid | ACTIVE, FINISHED |
| **Project** | Project to assign to | **A3:** Valid | PLANNED, SUSPENDED |
| **Worker** | To Assign | **B1:** null | null |
| **Worker** | To Assign | **B2:** Overloaded | 12 workload |
| **Worker** | To Assign | **B3:** Not Helpful | No qualifications, 1 useless qualification |
| **Worker** | To Assign | **B4:** Valid | Valid |

*BCC Table:*
| Test Cases | A (Project Status) | B (Project Qualifications) |
|------------|------------|
| **T1 (Base)** | PLANNED | Valid |
| **T2** | SUSPENDED | Valid |
| **T3** | ACTIVE | Valid |
| **T4** | FINISHED | Valid |
| **T5** | null | Valid |
| **T6** | PLANNED | 1 useless qualification only |
| **T7** | PLANNED | Full workload (12) |
| **T8** | PLANNED | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public void unassign()`

*ISP TABLE*
|**Variable**|**Partition**|**Values**|
|-----------|------------|----------|
|**Worker**|Null (W1)|`null`|
||Not Assigned (W2)|Worker not in project|
||Assigned, only project (W3)|Worker in project, only project|
||Assigned, multiple projects (W4)|Worker in project, has other projects|
|**Project**|Null (P1)|`null`|
||Valid (P2)|`PLANNED`, `SUSPENDED`, `ACTIVE`|
|**Project Status After Removal**|Remains the same (S1)|No missing qualifications|
||Changes to `SUSPENDED` (S2)|Missing qualifications|
|**Worker’s Pool**|Still assigned (A1)|Has other projects|
||Becomes available (A2)|No projects left after unassigning|

*BCC TABLE*
|**Test Case**|**Worker**|**Project**|**Initial Project Status**|**Worker Pool**|**Result**|
|------------|---------|---------|-------------------|------------|---------|
|**T1 (Base)**|Assigned, only project|`PLANNED`|`PLANNED`|Becomes available|Worker removed, no status change|
|**T2**|Assigned, only project|`SUSPENDED`|`SUSPENDED`|Becomes available|Worker removed, no status change|
|**T3**|Assigned, only project|`ACTIVE`|`ACTIVE` (no missing quals)|Becomes available|Worker removed, stays `ACTIVE`|
|**T4**|Assigned, only project|`ACTIVE`|`ACTIVE` (missing quals after removal)|Becomes available|Worker removed, project set to `SUSPENDED`|
|**T5**|Not Assigned|Any|Any|N/A|No change, method exits early|
|**T6**|Assigned, multiple projects|Any|Any|Still assigned|Worker removed, remains in `assigned`|
|**T7**|Assigned, only project|Any|Any|Becomes available|Worker removed from `assigned`, added to `available`|


*Test Cases Left to Cover:* **None!**

# PROJECT CLASS METHODS:

**METHOD:** `Project()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Name** | Name of the Project | **A1:** Null | null |
| **Name** | Name of the Project | **A1:** Empty String | Empty String |
| **Name** | Name of the Project | **A1:** String of whitespace  | String of Whitespace |
| **Name** | Name of the Project | **A1:** Valid String | Length 1, Length 2 |
| **Qualifications** | The set of all of the company's qualifications | **B1:** null | null |
| **Qualifications** | The set of all of the company's qualifications | **B2:** empty set | empty set |
| **Qualifications** | The set of all of the company's qualifications | **B3:** Has Qualifications | 1, 2 |
| **Size** | Size of the Project | **C1:** Null | Null |
| **Size** | Size of the Project | **C2:** Invalid Size | Invalid Size |
| **Size** | Size of the Project | **C3:** Valid Sizes | Small, Medium, or BIG |

*BCC Table:*
| Test Cases | A (Name) | B (Qualifications) | C (Size) |
|------------|----------|--------------------|----------|
| **T1 (Base)** | Length 2 | 2 Qualifications | Medium |
| **T2** | Length 1 | 2 Qualifications | Medium |
| **T3** | Whitespace | 2 Qualifications | Medium |
| **T4** | Empty | 2 Qualifications | Medium |
| **T5** | Null | 2 Qualifications | Medium |
| **T6** | Length 2 | 1 Qualifications | Medium |
| **T7** | Length 2 | Empty Set | Medium |
| **T8** | Length 2 | Null | Medium |
| **T9** | Length 2 | 2 Qualifications | BIG |
| **T10** | Length 2 | 2 Qualifications | Small |
| **T11(NOT TESTABLE)** | Length 2 | 2 Qualifications | Invalid |
| **T12** | Length 2 | 2 Qualifications | Null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public boolean equals(Object other)`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Other** | Object to compare to | **A1:** Null | null |
| **Other** | Object to compare to | **A2:** Invalid | Different Object Type, Different Name |
| **Other** | Object to compare to | **A3:** Valid | Equal Object |
| **Name** | Name of the Project | **B1:** Null | null |
| **Name** | Name of the Project | **B2:** Empty String | Empty String |
| **Name** | Name of the Project | **B3:** String of whitespace  | String of Whitespace |
| **Name** | Name of the Project | **B4:** Valid String | Length 1, Length 2 |

*BCC Table:*
| Test Cases | A (Other) | B (Name) |
|------------|-----------|----------|
| **T1 (Base)** | Equal Object | Length 2 |
| **T2** | Different Name | Length 2 |
| **T3** | Different Object Type | Length 2 |
| **T4** | null | Length 2 |
| **T5** | Equal Object | Length 1 |
| **T6(NOT TESTABLE)** | Equal Object | Whitespace String |
| **T7(NOT TESTABLE)** | Equal Object | Empty String |
| **T8(NOT TESTABLE)** | Equal Object | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public int hashCode()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Size** | Size of the Project | **A1:** Null | null |
| **Size** | Size of the Project | **A2:** Invalid | Invalid Size |
| **Size** | Size of the Project | **A3:** Valid | Small, Medium, BIG |
| **Name** | Name of the Project | **B1:** Null | null |
| **Name** | Name of the Project | **B2:** Empty String | Empty String |
| **Name** | Name of the Project | **B3:** String of whitespace  | String of Whitespace |
| **Name** | Name of the Project | **B4:** Valid String | Length 1, Length 2 |
| **Status** | Status of the Project | **C1:** Null | null |
| **Status** | Status of the Project | **C2:** Invalid | Invalid Status |
| **Status** | Status of the Project | **C3:** Valid | PLANNED, SUSPENDED, ACTIVE, FINISHED |

*BCC Table:*
| Test Cases | A (Size) | B (Name) | C (Status) |
|------------|----------|----------|------------|
| **T1 (Base)** | Medium | Length 2 | PLANNED |
| **T2** | BIG | Length 2 | PLANNED |
| **T3** | Small | Length 2 | PLANNED |
| **T4(NOT TESTABLE)** | Invalid | Length 2 | PLANNED |
| **T5(NOT TESTABLE)** | null | Length 2 | PLANNED |
| **T6** | Medium | Length 1 | PLANNED |
| **T7(NOT TESTABLE)** | Medium | Whitespace String | PLANNED |
| **T8(NOT TESTABLE)** | Medium | Empty String | PLANNED |
| **T9(NOT TESTABLE)** | Medium | null | PLANNED |
| **T10** | Medium | Length 2 | SUSPENDED |
| **T11** | Medium | Length 2 | ACTIVE |
| **T12** | Medium | Length 2 | FINISHED |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public String toString()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Project Name** | Name of the project | **A1:** Valid | "CS5Anniv" |
| **Workers** | Number of workers assigned | **B1:** No workers | 0 |
| **Workers** | Number of workers assigned | **B2:** 2 workers | "Nate", "Nick" |
| **Workers** | Number of workers assigned | **B3:** No workers | "Tim", "Luke", "Karen" |
| **Status** | The project's status | **C1:** Default | PLANNED |
| **Status** | The project's status | **C2:** Changed | ACTIVE |

*BCC Table:*
| Test Cases | A (Project Name) | B (Workers) | C (Status) |
|------------|-------------|----------|------------|
| **T1 (Base)** | "CS5Anniv" | 0 | PLANNED |
| **T2** | "CS5Anniv" | 2 | PLANNED |
| **T3** | "CS5Anniv" | 3 | PLANNED |
| **T4** | "CS5Anniv" | 0| ACTIVE |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public String getName()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Name** | Name of the Project | **A1:** Null | null |
| **Name** | Name of the Project | **A2:** Empty String | Empty String |
| **Name** | Name of the Project | **A3:** String of whitespace  | String of Whitespace |
| **Name** | Name of the Project | **A4:** Valid String | Length 1, Length 2 |

*BCC Table:*
| Test Cases | A (Name) |
|------------|----------|
| **T1 (Base)** | Length 2 |
| **T2** | Length 1 |
| **T3(NOT TESTABLE)** | Whitespace String |
| **T4(NOT TESTABLE)** | Empty String |
| **T5(NOT TESTABLE)** | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public ProjectSize getSize()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Size** | Size of the Project | **A1:** Null | null |
| **Size** | Size of the Project | **A2:** Invalid | Invalid Size |
| **Size** | Size of the Project | **A3:** Valid | Small, Medium, BIG |

*BCC Table:*
| Test Cases | A (Status) |
|------------|------------|
| **T1 (Base)** | Small |
| **T2** | Medium |
| **T3** | BIG |
| **T4(NOT TESTABLE)** | Invalid Size |
| **T5(NOT TESTABLE)** | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public ProjectStatus getStatus()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Status** | Status of the Project | **A1:** Null | null |
| **Status** | Status of the Project | **A2:** Invalid | Invalid Status |
| **Status** | Status of the Project | **A3:** Valid | PLANNED, SUSPENDED, ACTIVE, FINISHED |

*BCC Table:*
| Test Cases | A (Status) |
|------------|------------|
| **T1 (Base)** | PLANNED |
| **T2** | SUSPENDED |
| **T3** | ACTIVE |
| **T4** | FINISHED |
| **T5(NOT TESTABLE)** | Invalid Status |
| **T6** | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public void setStatus()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Status** | Status of the Project | **A1:** Null | null |
| **Status** | Status of the Project | **A2:** Invalid | Invalid Status |
| **Status** | Status of the Project | **A3:** Valid | PLANNED, SUSPENDED, ACTIVE, FINISHED |
| **New Status** | Status to set the projects to | **B1:** Null | null |
| **New Status** | Status to set the projects to | **B2:** Invalid | Invalid Status |
| **New Status** | Status to set the projects to | **B3:** Valid | PLANNED, SUSPENDED, ACTIVE, FINISHED |

*BCC Table:*
| Test Cases | A (Status) | B (New Status) |
|------------|------------|----------------|
| **T1 (Base)** | PLANNED | PLANNED |
| **T2** | SUSPENDED | PLANNED |
| **T3** | ACTIVE | PLANNED |
| **T4** | FINISHED | PLANNED |
| **T5** | Invalid Status | PLANNED |
| **T6** | null | PLANNED |
| **T7** | PLANNED | PLANNED |
| **T8** | PLANNED | SUSPENDED |
| **T9** | PLANNED | ACTIVE |
| **T10** | PLANNED | FINISHED |
| **T11** | PLANNED | Invalid Status |
| **T12** | PLANNED | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public void addWorker()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Workers** | Set of assigned workers | **A1:** Null | null |
| **Workers** | Set of assigned workers | **A2:** Empty Set | Empty Set |
| **Workers** | Set of assigned workers | **A3:** Valid | 1, 2 |
| **New Worker** | New worker to add | **B1:** Null | null |
| **New Worker** | New worker to add | **B2:** Valid | Valid Worker |

*BCC Table:*
| Test Cases | A (Workers) | B (New Worker) |
|------------|-------------|----------------|
| **T1 (Base)** | 2 | Valid |
| **T2** | 1 | Valid |
| **T3** | Empty Set | Valid |
| **T4(NOT TESTABLE)** | null | Valid |
| **T5** | 2 | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public void removeWorker()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Workers** | Set of assigned workers | **A1:** Null | null |
| **Workers** | Set of assigned workers | **A2:** Empty Set | Empty Set |
| **Workers** | Set of assigned workers | **A3:** Valid | 1, 2 |
| **Worker to Remove** | Worker to remove | **B1:** Null | null |
| **Worker to Remove** | Worker to remove | **B2:** Invalid Worker (not in Workers) | Invalid |
| **Worker to Remove** | Worker to remove | **B3:** Valid worker (in Workers) | Valid |

*BCC Table:*
| Test Cases | A (Workers) | B (Worker to Remove) |
|------------|-------------|----------------|
| **T1 (Base)** | 2 | Valid |
| **T2** | 1 | Valid |
| **T3** | Empty Set | Valid |
| **T4(NOT TESTABLE)** | null | Valid |
| **T5** | 2 | Invalid |
| **T6** | 2 | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public Set<Worker> getWorkers()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Workers** | Set of assigned workers | **A1:** Null | null |
| **Workers** | Set of assigned workers | **A2:** Empty Set | Empty Set |
| **Workers** | Set of assigned workers | **A3:** Valid | 1, 2 |

*BCC Table:*
| Test Cases | A (Workers) |
|------------|-------------|
| **T1 (Base)** | 2 |
| **T2** | 1 |
| **T3** | Empty Set |
| **T4(NOT TESTABLE)** | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public void removeAllWorkers()`

*ISP TABLE:*
| Variable  | Characteristics         | Partition  | Values   |
|-----------|-------------------------|------------|----------|
| **Workers** | Set of assigned workers | **A1:** Null  | N/A (Not possible in implementation) |
| **Workers** | Set of assigned workers | **A2:** Empty Set | `new HashSet<>()` |
| **Workers** | Set of assigned workers | **A3:** Valid | 1, 2 |

*BCC TABLE:*
| Test Cases  | A (Workers) |
|------------|-------------|
| **T1 (Base)** | 2 (Whiskers, Paws) |
| **T2** | 1 (Clawdia) |
| **T3** | Empty Set (`new HashSet<>()`) |
| **T4** | N/A (Not possible in implementation) |

**Test Cases Left to Cover:** **None** (All covered)

**METHOD:** `public Set<Qualification> getRequiredQualifications()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Qualifications** | The set of all of the company's qualifications | **A1:** null | null |
| **Qualifications** | The set of all of the company's qualifications | **A2:** empty set | empty set |
| **Qualifications** | The set of all of the company's qualifications | **A3:** Has Qualifications | 1, 2 |

*BCC TABLE:*
| Test Cases | A (Qualifications) |
|------------|--------------------|
| **T1 (Base)** | 2 |
| **T2** | 1 |
| **T3** | Empty Set |
| **T4(NOT TESTABLE)** | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public void addQualification()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Qualifications** | The set of all of the company's qualifications | **A1:** null | null |
| **Qualifications** | The set of all of the company's qualifications | **A2:** empty set | empty set |
| **Qualifications** | The set of all of the company's qualifications | **A3:** Has Qualifications | 1, 2 |
| **New Qualification** | Qualification to add to the company's set of qualifications | **B1:** null | null |
| **New Qualification** | Qualification to add to the company's set of qualifications | **B2:** Valid | Valid |

*BCC TABLE:*
| Test Cases | A (Qualifications) | B (New Qualification) |
|------------|--------------------|-----------------------|
| **T1 (Base)** | 2 | Valid |
| **T2** | 1 | Valid |
| **T3** | Empty Set | Valid |
| **T4** | null | Valid |
| **T5** | 2 | null |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public Set<Qualification> getMissingQualifications()`

*ISP TABLE:
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Qualifications** | The set of all of the company's qualifications | **A1:** null | null |
| **Qualifications** | The set of all of the company's qualifications | **A2:** empty set | empty set |
| **Qualifications** | The set of all of the company's qualifications | **A3:** Has Qualifications | Test, Test2, Test3, Test4, Test5, Test6 |
| **Workers** | Set of assigned workers | **A1:** Null | null |
| **Workers** | Set of assigned workers | **A2:** Empty Set | Empty Set |
| **Workers** | Set of assigned workers | **A3:** Workers with none of the Project's Qualifications met | "SirKittyCat" |
| **Workers** | Set of assigned workers | **A4:** Workers with some of the Project's Qualifications met | "MisterKitty" |
| **Workers** | Set of assigned workers | **A3:** Workers with all of the Project's Qualifications met | "Sir Kitty Cat" |

*BCC Table:*
| Test Cases | A (Qualifications) | B (Workers) |
|------------|--------------------|-------------|
| **T1 (Base)** | "Test", "Test2" | "SirKittyCat" |
| **T2** | "Test3", "Test 4" | "Mister Kitty" |
| **T3** | Empty Set | "SirKittyCat" |
| **T4** | null | "SirKittyCat" |
| **T5** | "Test5", "Test6" | Empty Set |
| **T6** | "Test3", "Test4"| "MisterKitty" |
| **T7** | "Test3", "Test4" | "SirKittyCat" |
| **T8** | "Test5", "Test6" | "MisterKitty" |
| **T9** | "Test5", "Test6" | null |
| **T10** | Empty set | "SirKittyCat" |

*Test Cases Left to Cover:* **None!**

**METHOD:** `public boolean isHelpful()`

*ISP TABLE:*
| Worker | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Worker** | The worker being audited | **A1:** null | null |
| **Worker** | Ther worker being audited | **A2:** valid | Worker Object |
| **Qualifications** | The set of all of the worker's qualifications | **B1:** No Qualifications | Empty set |
| **Qualifications** | The set of all of the worker's qualifications | **B2:** Some qualifications that help | "Testing2" |
| **Qualifications** | The set of all the worker's qualifications | **B3:** Some qualifications that are unhelpful | Not helpful |
| **Project Missing Qualifications** | Qualifications still needed | **C1:** Some Missing | "Testing1", "Testing2" |
| **Project Missing Qualifications** | Qualifications still needed | **C2:** All covered | Empty set |
| **Project Missing Qualifications** | Qualifications still needed | **C3:** Qualifications don't match | "Testing3", "Testing4"|


*BCC TABLE:*
| Test Cases | A (Qualifications) | B (New Worker) |
|------------|--------------------|----------------|
| **T1 (Base)** | 1 | Testing1 1 worker with qual |
| **T2** | 0 | 1 worker with no qual |
| **T3** | 2 | 1 worker with Qual |
| **T4** | 2 | 1 worker with Qual |
| **T5** | 2 | 1 worker not helpful qual |

*Test Cases Left to Cover:* **None**

**METHOD:** `public ProjectDTO toDTO()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Qualifications** | Set of qualifications | **A1:** Empty set | Empty set |
| **Qualifications** | Set of qualifications | **A2:** Single Qual | "Testing 1", "Testing 2" |
| **Qualifications** | Set of qualifications | **A3:** Mult Qual | "Testing 1", "Testing 2", "Testing 3", "Testing 4" |
| **Workers** | Set of assigned workers | **B1:** No workers | 0 |
| **Workers** | Set of assigned workers | **B2:** 1 worker | "Mister Dog" |
| **Workers** | Set of assigned workers | **B3:** Mult workers | "SirDog", "DoggieDog" |

*BCC TABLE:*
| Test Cases | A (Qualifications) | B (Workers) |
|------------|--------------------|-------------|
| **T1 (Base)** | Empty set | 0 workers |
| **T2** | 1 Qual | "MisterDog" |
| **T3** | Mult Qual | "SirDog", "DoggieDog" |

*Test Cases Left to Cover:* **1**

# QUALIFICATION CLASS METHODS:

**METHOD:** `public Qualification(String description)`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|--------------|------------------|-----------|--------|
| **Description** | The description of the qualification | **A1:** Null | null |
|  |  | **A2:** Empty String | "" |
|  |  | **A3:** Whitespace String | "   " |
|  |  | **A4:** Valid String | "Java Development", "Python Mastery" |

*BCC TABLE:*
| Test Cases | A (Description) |
|------------|----------------|
| **T1 (Base)** | "Java Development" |
| **T2** | "Python Mastery" |
| **T3** | null |
| **T4** | "" (empty string) |
| **T5** | "   " (whitespace string) |

*Test Cases Left to Cover:* **None**

**METHOD:** `public boolean equals(Object other)`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Other** | Object to compare | **A1:** Null | null |
|  |  | **A2:** Different Type | String, Integer |
|  |  | **A3:** Different Qualification | "Python Development" vs. "Java Development" |
|  |  | **A4:** Same Qualification | "Java Development" vs. "Java Development" |

*BCC TABLE:*
| Test Cases | A (Other) |
|------------|-----------|
| **T1 (Base)** | Same Qualification |
| **T2** | Different Qualification |
| **T3** | Null Object |
| **T4** | Different Type Object |

*Test Cases Left to Cover:* **None**

**METHOD:** `public int hashCode()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Description** | The description of the qualification | **A1:** Different Strings | "Java Development", "Python Development" |
|  |  | **A2:** Same String | "Java Development", "Java Development" |

*BCC TABLE:*
| Test Cases | A (Description) |
|------------|----------------|
| **T1 (Base)** | Same String |
| **T2** | Different Strings |
| **T3** | Consistency Check |

*Test Cases Left to Cover:* **None**

**METHOD:** `public String toString()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Description** | The description of the qualification | **A1:** Different Strings | "Java Development", "Python Development" |

*BCC TABLE:*
| Test Cases | A (Description) |
|------------|----------------|
| **T1 (Base)** | "Java Development" |
| **T2** | "Python Development" |

**METHOD:** `public Set<Worker> getWorkers()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Workers** | The set of workers with the qualification | **A1:** Empty Set | Empty Set |
|  |  | **A2:** One Worker | "Sir Fix-a-lot" |
|  |  | **A3:** Multiple Workers | "Captain Crunch", "Debugzilla" |

*BCC TABLE:*
| Test Cases | A (Workers) |
|------------|-------------|
| **T1 (Base)** | Empty Set |
| **T2** | One Worker |
| **T3** | Multiple Workers |

*Test Cases Left to Cover:* **None**

**METHOD:** `public void addWorker(Worker worker)`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Worker** | Worker to add | **A1:** Null | null |
|  |  | **A2:** Unique Worker | "Sir Fix-a-lot" |
|  |  | **A3:** Duplicate Worker | "Copy Cat" (added twice) |

*BCC TABLE:*
| Test Cases | A (Worker) |
|------------|-----------|
| **T1 (Base)** | Unique Worker |
| **T2** | Null Worker |
| **T3** | Duplicate Worker |

*Test Cases Left to Cover:* **None**

**METHOD:** `public void removeWorker(Worker worker)`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Worker** | Worker to remove | **A1:** Null | null |
|  |  | **A2:** Worker Exists | "The Code Whisperer" |
|  |  | **A3:** Worker Not in Set | "Semicolon Slayer" |

*BCC TABLE:*
| Test Cases | A (Worker) |
|------------|-----------|
| **T1 (Base)** | Worker Exists |
| **T2** | Worker Not in Set |
| **T3** | Null Worker |

*Test Cases Left to Cover:* **None**

**METHOD:** `public QualificationDTO toDTO()`

*ISP TABLE:*
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
| **Workers** | The set of workers with the qualification | **A1:** Empty Set | Empty Set |
|  |  | **A2:** One Worker | "Quack McFlapface" |
|  |  | **A3:** Multiple Workers | "Sir Laughs-a-Lot", "Duke of Dank", "Captain Chuckles" |

*BCC TABLE:*
| Test Cases | A (Workers) |
|------------|-------------|
| **T1 (Base)** | Empty Set |
| **T2** | One Worker |
| **T3** | Multiple Workers |

*Test Cases Left to Cover:* **None**

# WORKER CLASS METHODS:

**METHOD**: `public Worker()` 

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|-----------------|-----------|--------|
|**Name**  |Name of the worker| **A1** Valid String| Length >= 1|
|**Name**  |Name of the worker| **A2** Empty String| Empty String|
|**Name**  |Name of the worker| **A3** String of Whitespace| String of Whitespace|
|**Name**  |Name of the worker| **A4** null| null|
|**Qualifications**  |set of the worker's qualifications| **B1** null| null|
|**Qualifications**  |set of the worker's qualifications| **B3** Has Qualifactions| Set filled with >=1 qualifactions|
|**Salary**  |Worker's Salary| **C1** null| null|
|**Salary**  |Worker's Salary| **C3** Postive salary| > 0|

*BBC TABLE*
| Test Cases| A (Name) | B (Qualifications) | C (Salary)|
|----------|-----------------|-----------|--------|
|T1 (Base) | "Worker"| Qualifaction(Testing 1) | 15 | 
|T2  | null | Qualifaction(Testing 1) | 15 | 
|T3 | "" | Qualifaction(Testing 1) | 15 | 
|T4 | "     " | Qualifaction(Testing 1) | 15 | 
|T5 | "Worker1"| null | 15|
|T6 | null| null | null|
|T7 | "Worker1"| null | 0|

*Test cases left to cover:* **TBD**

**METHOD**: `public boolean equals`

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
| **OTHER** | Object to compare | A1: Null | null |
| **OTHER** | Object to compare | A2: Different Class type | Any other Class |
| **OTHER** | Object to compare | A3: Different Name right class | "Worker 1" vs "Worker 2 |
| **OTHER** | Object to compare | A4: Same Name | "Worker 1" vs "Worker 1 |

*BCC TABLE*:

| Test Cases | A (Other) |
|----------|----------------|
|T1 (Base) | Same Name |
|T2 | Different Name |
|T3 | Null Object |
|T4 | Different class |
*Test cases left to cover:* **none**



**METHOD**: `public int hashcode()`

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
| **Name** | Worker's name| **A1** Same String | "Worker 1", "Worker 1"|
| **Name** | Worker's name| **A2** Different String | "Worker 1", "Worker 2"|

*BCC TABLE*:
|Test Case| A (Name) |
|---------|----------|
|T1 (Base) | Same String|
|T2 | Different String|
|T3 | Consistency Check| 

*Test cases left to cover:* **none**

**METHOD** : `public String toString`

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
| **Worker Name** | Name of Worker | **A1**: valid string | valid string |
| **Qualifactions** | Qualifactions of worker | **B1**: Empty set | Empty set|
| **Qualifactions** | Qualifactions of worker | **B2**: Filled set | 2 qualifactions|
| **Projects** | Project for the worker| **C1** Empty set |  Empty set|
| **Projects** | Project for the worker| **C2** Filled set |  2 projects|
| **Salary** | Salary of Worker| **D1** valid double |  double |

*BCC TABLE*:

|Test Case | A (Name) | B (Qualifactions) | C (Projects)| D (Salary) |
|----------|----------------|-----------|--------|-----|
|T1 (Base Case) | "Sir Nick the Debugger" | "Code Wizard", "Certifieed Caffeine Consumer" | Empty set| 10000.99|
|T2 | "Dr. Alex the Query Slayer" | "Python Whisper", "Database Tamer" | "Infinite Loop Fixer", "Bug Exorcism"| 45000.75|
|T3 | "Jamie the Intern" | Empty set | Empty set | 0 |

*Test cases left to cover:* **none**

**METHOD**: `public String getName()`

*ISP TABLE*:

| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Name** | Name of worker| **A1** Null | null |
|**Name** | Name of worker| **A2** Empty String | Empty |
|**Name** | Name of worker| **A3** String of whitespace | String of whitespace |
|**Name** | Name of worker| **A4** Valid String | Length > 1 |

*BBC TABLE*:

|Test Case | A (Name) |
|----------|----------|
|T1 (Base Case) | Valid String: "Worker 1", "Worker 1 |
|T2 | null|
|T3 | "" |
|T4 | "     " |

*Test cases left to cover:* **none**

**METHOD** `public double getSalary()`

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Salary**| Worker's salary | **A1**: valid double| double > 0 |
|**Salary**| Worker's salary | **A2**: valid double| double == 0|

*BCC TABLE*:

|Test Case| A (Salary)|
|T1 (Base Case) | 5.0 |
|T2 | 0.0|

*Test cases left to cover:* **none**

**METHOD**: `public void setSalary(double)`

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Salary**| Current worker salary| **A1**: valid | double > 0|
|**Salary**| Current worker salary| **A1**: invlaid | 0 <|
|**New Salary**| New worker salary| **B1**: valid | double|

*BCC Table*:

|Test Case| A (Salary)| B (New Salary)|
|---------|-----------|---------------|
|T1 (Base Case)| 5.0 | 5.0|
|T2 | 250 | 5 |
|T3 | 250| 250|
|T4 |-5 | not equal|

*Test cases left to cover:* **none**

**METHOD**: `public Set<Qualification> getQualifactions()`

*ISP TABLE*:

| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Qualifactions** | set of Qualifactions | **A1**: Empty Set| Empty Set|
|**Qualifactions** | set of Qualifactions | **A2**: Non-Empty set| set<Qualifactions>|
|**Qualifactions** | set of Qualifactions | **A3**: null | null |
|**constructor Qualifactions** | set of Qualifactions passed to Constructor | **B1**: Empty set| set<Qualifactions>|
|**constructor Qualifactions** | set of Qualifactions passed to Constructor | **B2**: Non-Empty set| set<Qualifactions>|
|**constructor Qualifactions** | set of Qualifactions passed to Constructor | **B3**: null | null|

*BBC Table*: 

|Test Case| A (Qualifactions)|
|---------|------------------|
|T1 (Base Case)| Non-Empty: "G Suite", "Not Talking"| Empty |
|T2 | Empty | "Excel|
|T3 | Non-Empty: "G Suite", "Not Talking"| "Excel"|
|T4 | Emtpy| Empty|
|T5 | null | null |

*Test cases left to cover:* **none**

**METHOD**: `public void addQualifactions()`

*ISP TABLE*:

| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Qualifactions** | set of Qualifactions | **A1**: Empty Set| Empty Set|
|**Qualifactions** | set of Qualifactions | **A2**: Non-Empty set| set<Qualifactions>|
|**Qualifactions** | set of Qualifactions | **A3**: null | null |
|**constructor Qualifactions** | set of Qualifactions passed to Constructor | **B1**: Empty set| set<Qualifactions>|
|**constructor Qualifactions** | set of Qualifactions passed to Constructor | **B2**: Non-Empty set| set<Qualifactions>|
|**constructor Qualifactions** | set of Qualifactions passed to Constructor | **B3**: null | null|

*BBC TABLE*: 

|Test Case| A (Qualifactions)|
|---------|------------------|
|T1 (Base Case)| Non-Empty: "G Suite", "Not Talking"| Empty |
|T2 | Empty | "Excel|
|T3 | Non-Empty: "G Suite", "Not Talking"| "Excel"|
|T4 | Emtpy| Empty|
|T5 | null | null |

*Test cases left to cover:* **none**

**METHOD** `public set<Project> getProjects()`

*ISP TABLE*:

| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Projects** | set of Projects | **A1**: Empty Set| Empty Set|
|**Projects** | set of projects | **A2**: Non-Empty set| set<Project>|

*BBC TABLE*:
|Test Case| A (Project)|
|---------|------------------|
|T1 (Base Case)| Non-Empty set |
|T2 | Empty set |

*Test cases left to cover:* **none**

**METHOD** `public void addProject(Project project)`

*ISP TABLE*:

| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Project** | Project | **A1**: valid Project| Project|
|**Project** | Project | **A2**: Null| Null|
|**Size** | size of Projects | **B1**: empty project | 0|
|**Size** | size of Projects | **B1**: filled project | > 0|

*BBC TABLE*:
|Test Case| A (Project)| B (Size)|
|---------|------------|-------------|
|T1 (Base Case)| valid Project("Monica the Gen Z")| 1 |
|T2 | valid Project("Monica the Gen Z")| 2 projects alreadyed filled |
|T3| Null| 0|

*Test cases left to cover:* **none**

**METHOD** `public void removeProject(Project project)`

*ISP TABLE*:

| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Project** | Project | **A1**: valid Project| Project|
|**Project** | Project | **A2**: Null| Null|
|**Size** | size of Projects | **B1**: empty project | 0|
|**Size** | size of Projects | **B1**: filled project | > 0|

*BBC TABLE*:
|Test Case| A (Project)| B (Size)|
|---------|------------|-------------|
|T1 (Base Case)| valid Project("Spelling your name with a C")| 0 |
|T2| Null| 0|

*Test cases left to cover:* **none**

**METHOD**: `public int getWorkload(Object other)`

*ISP TABLE*:

| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
| **Project Status** | Whether the project contributes to workload | **A1:** FINISHED | Ignored (0) |
| **Project Status** | Whether the project contributes to workload | **A2:** ACTIVE | Counted (1/2/3) |
| **Project Status** | Whether the project contributes to workload | **A3:** PLANNED | Ignored (0) |
| **Project Size** | Workload added if status is ACTIVE | **B1:** SMALL | +1 |
| **Project Size** | Workload added if status is ACTIVE | **B2:** MEDIUM | +2 |
| **Project Size** | Workload added if status is ACTIVE | **B3:** BIG | +3 |
| **Number of Projects** | Total projects assigned to the worker | **C1:** 0 projects | 0 |
| **Number of Projects** | Total projects assigned to the worker | **C2:** 1 project | 1 |
| **Number of Projects** | Total projects assigned to the worker | **C3:** Multiple projects | 2+ |

**Notes**:
- **FINISHED (A1)** or **PLANNED (A3)** projects do not add to workload.
- **ACTIVE (A2)** projects add 1, 2, or 3 to the workload based on size (SMALL, MEDIUM, BIG).
- **Number of Projects** determines how many times contributions are summed.

---

*BCC TABLE*:
| Test Cases | A (Project Status) | B (Project Size) | C (# Projects) | Oracle |
|------------|-------------------|-----------------|----------------|---------------------|
| **T1 (Base)** | A2 (ACTIVE) | B2 (MEDIUM) | C2 (1 project) | 2 (MEDIUM adds 2 if ACTIVE) |
| **T2** | A1 (FINISHED) | B2 (MEDIUM) | C2 (1 project) | 0 (FINISHED is ignored) |
| **T3** | A3 (PLANNED) | B2 (MEDIUM) | C2 (1 project) | 0 (PLANNED is ignored) |
| **T4** | A2 (ACTIVE) | B1 (SMALL) | C2 (1 project) | 1 (SMALL adds 1 if ACTIVE) |
| **T5** | A2 (ACTIVE) | B3 (BIG) | C2 (1 project) | 3 (BIG adds 3 if ACTIVE) |
| **T6** | A2 (ACTIVE) | B2 (MEDIUM) | C1 (0 projects) | 0 (no projects = 0 workload) |
| **T7** | A2 (ACTIVE) | B2 (MEDIUM) | C3 (multiple) | Sum (1 + 2 + 3 = 6) |

**Oracle Explanations**:
- **0**: Workload is zero (no active projects, or projects are finished/planned).
- **Positive integer**: Sum of contributions from all **ACTIVE** projects.

**METHOD**: `public boolean willOverload()`

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Projects Size**| size of the projects assigned to worker| **A1**: workload < 12| Set with a workload <12 |
|**Projects Size**| size of the projects assigned to worker| **A2**: workload >= 12| Set with a workload >=12 |
|**Project Size** | project getting added to worker| **B1**: ProjectSize not overload| ProjectSize to not overload|
|**Project Size** | project getting added to worker| **B2**: ProjectSize to overload| ProjectSize to overload|

*BBC TABLE*
|Test Case| A (ProjectSize) | B (Project)|
|----------|----------------|-----------|
|T1 (Base Case) | (ProjectSize.BIG + ProjectSize.BIG + ProjectSize.BIG + ProjectSize.SMALL) | ProjectSize.SMALL| 
|T2 | (ProjectSize.BIG + ProjectSize.BIG + ProjectSize.SMALL + ProjectSize.SMALL + ProjectSize.MEDIUM) | ProjectSize.BIG| 
*Test cases left to cover:* **none**

**METHOD** `public boolean isAvailable()`

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Projects Size**| size of the projects assigned to worker| **A1**: workload < 12| Set with a workload <12 |
|**Projects Size**| size of the projects assigned to worker| **A2**: workload >= 12| Set with a workload >=12 |

*BBC TABLE*
|Test Case| A (ProjectSize) |
|----------|----------------|
|T1 (Base Case) | (ProjectSize.BIG + ProjectSize.BIG + ProjectSize.BIG)| 
|T2 | (ProjectSize.BIG + ProjectSize.BIG + ProjectSize.SMALL + ProjectSize.SMALL + ProjectSize.MEDIUM + ProjectSize.BIG) |
*Test cases left to cover:* **none**

**METHOD** `public WorkerDTO toDTO()`

*ISP TABLE*:
| Variable | Characteristics | Partition | Values |
|----------|----------------|-----------|--------|
|**Qualifactions**| number of Qualifactions| **A1**: One Qualifaction| Qualifactions.size==1|
|**Qualifactions**| number of Qualifactions| **A2**: Multiple Qualifaction| Qualifactions.size>1|
|**Name**| Name of worker | **B1**: String| length >=1|
|**Salary**| Salary of worker | **C1**: double| >=0|
|**Project Names** | Name of Projects| **D1**: empty string | empty|
|**Project Names** | Name of Projects| **D1**: assigned projects | has >0 projects assigned|

*BBC TABLE*:
|Test Case| A(Qualifactions)| B (Name) | C (Salary) | D (Project Names)|
|---------|-----------------|----------|------------|------------------|
|T1 (Base Test)|One Qualifaction: "Excel"| "Steve the Woz" | 50.0 | no Projects|
|T2 | multiple Qualifactions "Roblox", "Minecraft", "Getting Angy at little kids"| "The dude"| 25.0| no Projects
|T3 | multiple Qualifactions "Roblox", "Minecraft", "Getting Angy at little kids"| "The dude"| 25.0| project assigned|

*Test cases left to cover:* **none**