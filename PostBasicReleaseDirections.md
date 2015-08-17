# Introduction

The aim of the current project is to simulate the experience of football from the match engine to the competition level 
and maybe beyond. While the core of such simulation would be the match engine, it would be uninteresting if the 
surrounding ecosystem was ignored (or neglected, for that matter).

While my core interests lie in the simulation of a football match, not necessarilly in the form of a full-blown 3D 
eye-candy match engine (although that would also be desirable); after all there are so many ways in which you can 
simulate a football match, by focusing on different aspects (this would be the scope of a different document), 
the current document is about the functionality having to do with the football competition logistics.

The first release (0.1) supports basic knockout tournament templates and round-robin competitions, with top European clubs 
and nations, however there are quite a few ways and directions for this project to be grown. This document will present 
those, out of which a backlog of technical issues (tasks) will be created. Feedback (and even contributions) will be 
appreciated from anyone interested. While I am currently working on my own stuff full-time and I will continue to do so
for at least the next few months, whatever will be described in this document is only part of my total work (consuming 
rougly 1/12th of my time). The reasons for this document are, first organizational (articulating my own plans beyong 
draft handwritten notes), second prioritization (feedback would help in that direction), third advertising and promotion
(spread the word about the project and invite potential contributors) and fourth feedback about the project itself (by
contributing new ideas and expressing opinions about the project in general). I thank you in advance for reading this and
for any feedback you may provide.

# Categories

The feature to propose are separated into seven categories: Input, Research, Data Entry, Continuity, Competitions, Customization, Persistence. Analyzing the rationale for each feature will also make the project's scope clearer.

## Input

The project in its current state has as input a number of teams, their ranking and their real life scores history as sample and simulates various competitions. Currently this simulation is initiated as a unit test, so depending of what method you call, the corresponding competition is simulated. The competititons formats templates supported are a knockout competition with "neutral" venue single match round, a knockout competition with two-legged rounds (single match for the final) and round-robin competition. The teams currently "supported" (provided by default in the data set) are top European clubs and top nations (based on an ad-hoc ranking, taking into account latest competitions' performance, Elo ranking and FIFA ranking). So, we can already support for example 4 different competition formats (knockout and round robin format times clubs and nations).

So, how are the competition participants selected by default? In general, the participants for each competition are passed as parameter. (I won't be going in any more technical details at this document. That would belong to a separate explicit API documentation). Conventionally, there is no limit in the number of participants in a knockout competition, neither has there be a specific number; the number of rounds adjusts itself according to the number of participants. Suppose, for example, that there are 64 participants in a knockout competition: then we would have 4 different rounds, a semi-final round and the final. If the number of participants is not a power of two (as it is usually the case in real life), then there will be a preliminary round (in which the lowest ranked teams participate), so that in the first "proper" round a power of two number of teams participates. In our original European clubs ranking, for example, we have 68 teams. In order to have 64 teams in the first round (the maximum power of two number less than the original number of 68), we need to eliminate 4 teams, so the preliminary round should have 4 pairs = 8 participants with the winners joining the rest of thre 60 teams for the first round.

An implicit feature here partly supported is that of seeding. We use a seeding (the ranking) so that we can decide which teams should play in the preliminary round (it could have been totally random, as well), but after that there is no seeding in the draws. I am mentioning that here as this is the default hard-coded behaviour, but this is a feature to be added (and provided as parameter).

For round-robin competitions theoretically we could have an arbitrary number of participants (as there are no real life logistics to take care of here and we could knock ourselves out with a round-robin tournament of 42 teams), but for sticking to real life situations I added some utility method for filtering a number of top ranked teams for the purpose of using its return value as input for the teams participating. 

After this not so short introduction of what currently works, I will go to the meat of the proposed future directions and features for this category. Specifically, the different ways to specify participants in a competition. (Suppose we get the issue of the competition format out of the way for now; this is going to be addressed separately as a category of its own and it spans this and one other category at least).

Based on the above, we currently can only specify the number of participants in a competition based on pre-defined ranking provided in data resources (someone could only tweak the rankings as a workaround to select explicitly the participating teams). I distinguish four different ways participant teams can be selected: random, ranking-based, fixed (user-specified) and retro (historic).

