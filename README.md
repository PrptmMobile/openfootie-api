
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

## Research

In this category, I include a number of research-oriented tasks, with some of them having more practical application than others. These are the dynamic definition of a competition, analysis of plausability of scores, analysis of variance between different competition results, analysis of competition history and normalization of scores.

### Dynamic definition of competition

So far, we support specific competition formats with predefined rules, because it was easier to solve the problem bottom-up than generalizing from the start. There are many ways with which we could define a competition dynamically, from parameterizing a monolithic competition class to providing a special domain language. These options will be explored as part of this task. 

### Plausability analysis

One issue is how to measure plausability of scores (and another one is whether such quantification would be useful for a mere entertainment application). Our aim is to have a balance between predictability and plausability in our results. A too predictable result will for sure be realistic, but boring. However, random scores all over the place would spoil the expectation of a certain feel of realism. So in this task we will, almost philosophically, attemt to define and quantify plausability and then evaluate the results produced.

### Variance analysis

This mostly has to do with the results (league tables) of round robin competitions. This is interesting, because we will also research real-life leagues and see the variance between team performance in different leagues.

### Competition history analysis

Quite obviously, we can run a competition an infinite number of times. We could also provide some facilities for saving and analyzing this history (discussed in another section). This repetition of experiment is not possible in real life, of course. What we see in real life is a data point of the whole "experiment". The repetition of a competition shifts across time, so never the exact same competition can be replayed. What I am aiming for with this analysis is the comparison, with the best of approximations, of pseudo-historical simulation results with the corresponding real life history. More like the evaluation of the plausability of the competition results on a number of runs. Could we also evaluate the real life data points, in terms of how likely they were in the first place? In addition, how could we simulate changes in the ranking if we supported such feature, so that we also simulate competition history? (One way would be to use the existing team results to denote change in relative strength. A second way would be to associate a team with a more diachronic reputation, affecting how the team's strength changes over different runs).

### Normalization of scores

Finally, I conclude this category with a task that is of more practical value and would enhance both plausability and unpredictability directly.

The scores are generated based on a pool of real-life scores, taking into account the relative strength of opponents, as expressed by a custom ranking. While this works satisfactorily enough, if the sample of scores is relatively small, some scores might seem to be repeated often enough to be noticed. In the meantime some scores would be entirely missing (not as directly noticeable itself). The idea of this task is to introduce (i.e. generate automatically and insert) virtual scores in the selected sample according to the existing distribution of scores in the sample, so that the sample is richer and the results generated less predictable and more realistic in terms of variety.

## Data entry

Current data used by the project from the end-user's perspective is the names of teams; clubs and nations. Clubs names will be added accordingly with the addition of relevant competition. Currently, the only direct addition to enrich the project is that of nation teams and their integration in the ranking, for the sake of completeness. 

## Continuity

There is no reason to actively support temoral continuity between runs, since the end-user would not affect the results anyway (however, check also "Competition history analysis" above). The only form of continuity that would make sense in the current state of the project is that between "feeder" competitions to other ones, as for example when the domestic champions qualifying for the European cups.

## Competitions

Apart from the issue of user-defined competitions already mentioned, in the meantime we should continue to add competition templates based on well-known competitions. These are, for example, the Champions League, the World Cup (with or without taking into account the qualifiers), etc. Of course, domestic competitions are already simulated by the round-robin format. For exact replication of a real-life competition, we would need the competition template itself and the definition of input. However, we could also have such combination templates, so that for example we could restrict our round-robin competition to 20 English teams, so that the Premier League is simulated (which teams will exactly participate is ultimately left to the user).

## Customization

The only customization that an end-user could do right now is tweak the ranking of teams. (The changing of the sample scores is not counted as pure "end-user" activity). It's not hard, but it would also be better to write some documentation on how to change the ranking (and also where the default ranking(s) came from), and/or facilitate the user to perform this kind of change with a utility, and/or provide a utility that generates teams' rankings based on various parameters (more interesting that last one).

## Persistence

Finally, maybe a trivial feature for the time being, but useful for the future, as the outputs (and inputs) to the match engine will become more complex is the capability of saving outputs as historical data.


