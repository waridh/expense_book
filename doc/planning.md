# CMPUT 301 Planning

This document contains the outline of how I want the interactions to work on this android app for expense tracking.

## Goal of the program

- Track subscriptions
  - Add new expense
  - Edit previous expense
  - Delete expense
- Display the expenses
  - Minimally, need to display the following fields:
    - name
    - date
    - monthly charge
- User aid
  - Enforce the type of the input in each of the field

The data that is stored also have to be in a certain format, so we should be doing a check on the correctness of the user input.

## UML Diagram

![The UML Diagram](uml/assign1UML/assign1UML.png)

## TODO

| Task | Description |
| --- | --- |
| Make the main activity | For now, try to make the list view for the application |
| Making the user input activity | Need to get the activity made |

## Research

### Can you make a database on android studio

The constraint of the assignment says that we do not have to make the data persistent between runs.

#### Options

- sqlite
  - Might be a little overkill
- internal storage
- external storage
  - Not going to be available.

The research does not need to continue because this is not a required feature.
