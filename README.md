# Agent Soccer Team

A Multi-Agent Soccer team for SYSC 5103 using Jason.

## Contributors

JoshuaSFryer - Joshua Fryer

smartinstot - David Bascelli

TaylorAbraham - Taylor Abraham

ZeinHajjAli - Zein Hajj-Ali

## How to compile

From the project's root directory:

```
ant -f bin/agent_soccer_team.xml jar
```

Note that ant _must_ be installed, as the ant JAR provided by Jason doesn't accept command line arguments which are needed to specify the `build.xml` location.

## How to run

### Prerequisites

- A RoboCup server must be running _locally_
- This agent must be the first agent to run so that they are the left-side team

### Method 1 - Directly running the JAR

Run the `agent_soccer_team.jar` file by simply double-clicking it.

### Method 2 - Running via command line

```
java -jar agent_soccer_team.jar
```
