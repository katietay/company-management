# P1 Coverage Report

## Overview
As part of our continuous integration (CI) workflow, we generate a coverage report to check how well our tests cover the code. While the CI automatically creates this report, it's important to take a closer look at the results ourselves. This document breaks down the coverage details and provides some insights into what’s working well and where we can improve.

## Coverage Report
After running `mvn test`, you can find the coverage report in `server/target/site/jacoco`. it is labeled `jacoco.csv` Below is a summary of how much of our code is covered by tests in key classes: `Company`, `Project`, `Worker`, and `Qualification`.


|CLASS           |INSTRUCTION_MISSED|INSTRUCTION_COVERED|BRANCH_MISSED|BRANCH_COVERED|LINE_MISSED|LINE_COVERED|COMPLEXITY_MISSED|COMPLEXITY_COVERED|METHOD_MISSED|METHOD_COVERED|
|----------------|------------------|-------------------|-------------|--------------|-----------|------------|-----------------|------------------|-------------|--------------|
|Qualification   |0                 |114 (100%)         |0            |16 (100%)     |0          |28          |0                |16                |0            |8             |
|Company         |26                |589 (95%)          |16           |110 (87%)     |7          |128         |16               |67                |0            |20            |
|Worker          |0                 |207 (100%)         |0            |20   (100%)   | 0         |47          |0                |27                |0            |17            |
|Project         |2                 |323 (99%)          |1            |35   (97%)    |1          |67          |1                |34                |0            |17            |



## Analysis & Reflection
- **What’s working well?**
  - We as a team did great creating coverage for Worker and Qualifaction for both branches and overall instruction.
  - Overall as a team we did a good job having instruction coverage above 95% for all classes. Since you can't always test for everything and get all covered we are proud.

- **What needs improvement?**
  - We need to improve coverage in Company. This was one of our last classes to be implemented. Lack of time was the cause for the lesser coverage.
  - A lot of the last minute method for company have sub 90% coverage (getAvailableWorkers, getUnavailableWorkers, createQualification, unassignAll, equals)

- **How can we improve?**
  - Study the Jacoco HTML coverage and see which sections of Company isn't being covered. 
