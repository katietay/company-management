# P2 Mutation Report

## Mutation Scores (Before and After)

*Before changes:*
| Class | Line Coverage | Mutation Coverage | Test Strength |
|-------|---------------|-------------------|---------------|
| **Overall** | 290/298 (97%) | 153/165 (93%) | 153/161 (95%) |
| **Company** | 136/143 (95%) | 78/85 (92%) | 78/82 (95%) |
| **Project** | 72/73 (99%) | 28/32 (88%) | 28/31 (90%) |
| **ProjectSize** | 6/6 (100%) | 1/1 (100%) | 1/1 (100%) |
| **Qualification** | 29/29 (100%) | 16/16 (100%) | 16/16 (100%) |
| **Worker** | 47/47 (100%) | 30/31 (97%) | 30/31 (97%) |

*After changes:*
| Class | Line Coverage | Mutation Coverage | Test Strength |
|-------|---------------|-------------------|---------------|
| **Overall** | 288/288 (100%) | 156/156 (100%) | 156/156 (100%) |
| **Company** | 133/133 (100%) | 76/76 (100%) | 76/76 (100%) |
| **Project** | 73/73 (100%) | 32/32 (100%) | 32/32 (100%) |
| **ProjectSize** | 6/6 (100%) | 1/1 (100%) | 1/1 (100%) |
| **Qualification** | 29/29 (100%) | 16/16 (100%) | 16/16 (100%) |
| **Worker** | 47/47 (100%) | 31/31 (100%) | 31/31 (97%) |


## Reflection

The mutations highlighted quite a few oversights in our design. First off,
we had quite a few places in our main classes where we checked to see if global
variables were set to null. While this is useful, all of these methods are defined
in the constructor. This led to cases that couldn't possibly tested as they can't
be reached. I removed these very such cases as they are redundant.

Furthermore, there were a handful of locations where operators such as (12 > 0) did
not test for (12 >= 0) and vice versa. Tests were created for such cases.

I also found glaring issues in our logic in Company relating to assigned workers. We
have a global variable to store these workers, but our getAssignedWorkers() method
did not interact with it at all. Also, unassign didn't use it. I edited the main class
to allign the methods

Lastly, there were a case where we would check to see if a worker was available and then
also check to see if adding a new project would overload it. The first check will catch 
every case that the second would, so I changed the logic to remove the second redundant
check.

Overall, our initial scores were fairly good. However, we have now reached 100% coverage
in all 3 columns.
