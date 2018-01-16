# dals

An artificial life simulator that runs distributed using a island model.

DALS (Distributed Artificial Life Simulation), is a system that enables you to simulate artificial life over a distributed system. Basically, it enables you to run a simulation of life-forms in one environment, you can setup the environment attributes, such as: ammount of food available, food production rate, size, speed, environment influence over mutations (like some kind of radioactivity), etc. You can run the simulation in background or you can watch it! While watching, you'll see life-forms walking through a matrix-like map, eating, reproducing, hunting and dying.

Each individual runs in his own thread and has a lot of characteristics defined by his gens, like: metabolic speed, olfaction's reach (used in reproduction), vision's reach (used in feeding), movement distance, life expectation, sexual maturity age, etc. Some of this characteristics are represented directly in the image of the individual on the visualization screen.

You can link multiple environments, and this link can be done through the internet (it uses XMPP for communication), when you link one environment, an individual that reaches the defined frontier (one of the four sides of the map) will be transported to the new environment and will live there. This way, you can simulate an island model, and can generate alien input of genetic diversity in your environment.

Introduction

DALS enables you to simulate artificial life over a distributed system in a really easy way. It is written in java, in a compreensive way for you to hack it around.

==

DALS (Distributed Artificial Life Simulation), is a system that enables you to simulate artificial life over a distributed system. Basically, it enables you to run a simulation of life-forms in one environment, you can setup the environment settings like: ammount of food available, food production rate, environment phisical size, time speed, environment influence over mutations (radioactivity), etc. You can run the simulation in background or you can watch it! While watching, you'll see life-forms walking throught a matrix-like map, eating, reproducing, hunting and dying.

Each individual runs in his own thread and have a lot of characteristics defined by his DNA, like: metabolic speed, range for smell (used in reproduction), range for vision (used in feeding), range for movement, life expectation, sexual maturity age, etc. Some of this characteristics are represented directly in the image of the individual on the visualization screen - An individual with a long range for movement, have a bird-shape, an individual with a good vision range is represented with big eyes.

You can link environments, and this link can be done throught the internet (it uses XMPP for communication), when you link one environment, an individual that reaches the defined frontier (one of the four sides of he map) will be transported to the new environment and will live there. This way, you can simulate an island model, and can generate alien input of genetic diversity in your environment.

http://i.imgur.com/szaoj.png http://i.imgur.com/IpAPl.png http://i.imgur.com/bd3As.png

==

|Bits|Gen|Effect| |:-------|:------|:---------| |3 |Food A preference|Increases the probability of this individual to choose the A food type| |3 |Food B preference|Increases the probability of this individual to choose the B food type| |1 |Food type|Defines if this individual is an food type A or B| |10 |Vision |1 positioning block to each active bit| |10 |Olfaction|1 positioning block to each active bit| |10 |Maximum movement|1 positioning block to each active bit| |10 |Turn interval|0.1 second to each active bit| |5 |Extra energy consumption|1 extra point to each active bit| |5 |Sexual maturity time|3 turns to each active bit| |10 |Longevity|5 turns to each active bit|

==

The simulation of artificial life is something that has interested scientists since the beginning of computing science, and among these interested ones we can find names like John von Neumann, Alan Turing and other notables. The ability, using a computer and a set of rules, to obtain behaviors similar to those observed in live beings in real world is something of great value to us and brings to mind a range of possibilities for its application, since biology studies where it can, for example, simulate the balance of a given ecosystem, as the possibility of artificial beings evolve until the attainment of a goal either. Evolutionary algorithms have been used frequently and successfully in optimization problems, but currently projects in the attempt to create realistic simulations are rare. It is with the same motivation of John Holland, creator of genetic algorithms, that this work was designed, with the objective that we can experience nature-based and thus understand it better and take advantage of a formula that worked in nature for millions of years - natural evolution - to develop our algorithms for a given goal. It is quite understandable that for a long time has been left out the intent of simulating ecosystems with individuals due to the great complexity of such a simulation, since the establishment of rules that define the behaviors and interactions, until the computational capacity to process these rules in populations for several generations simulated. This project is an attempt to produce software that exploring some current computational capabilities such as parallel computing and distributed systems may include a large set number of rules and interactions, higher than what has traditionally been used in evolutionary algorithms. Thus, the simulations performed using the software presented here raise a number of individuals that compete for survival, feeding, moving, reproducing and that can migrate between the various ecosystems that may be connected in the simulation, each ecosystem has it's own characteristics and population. Through the application that was generated as a result of this project, we obtained a tool for the simulation of ecosystems with the capability of emerging behaviors such as balance, adaptation, extinction, competition, predators and others. The generated application, due to its extensibility, goes beyond a simulator and can serve as a basis for future simulation systems that make use of complex interactions between individuals, and that use distributed systems to support the processing of such simulations.

==
Dependencies

Smack 3.1.0

Used for XMPP communication.

Site: http://www.igniterealtime.org/projects/smack/

License: Apache

XStream 1.3

Used for object serialization.

Site: http://xstream.codehaus.org/

License: BSD

==
