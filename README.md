# vanillaPSO
Completed in 2017, this is a project where the purpose was to implement a vanilla PSO and investigate the performance using different parameter configurations, comparing the performance to random search. Random search, in this context, refers to a uniform random sampling of the search space. This can be accomplished by sampling and evaluating random (feasible) points uniformly within the search space.

The PSO algorithm is a stochastic search technique that is applicable to real-valued optimization problems. However, it’s performance is rather sensitive to the values assigned to its control parameters, namely the inertia weight, the cognitive acceleration coefficient (c1), and the social acceleration coefficient (c2). Poor parameter values can lead to performance that is even worse than random search

A few details on the PSO Algorithm:
- Uses the Rastrigin function
- Initialization: a random initial population of particles, each particle has a valid initial position and velocity. Typically, a swarm will consist of 20–40 particles.
- Neighbourhood Topology: a global-best neighbourhood such that the neighbourhood best refers to the best solution found by any particle throughout the course of the search.
- Boundary Constraints: particles are permitted to exit the feasible region, but only allow updating of the personal best when a solution is feasible.
- Iteration Strategy: a synchronous iteration strategy.
- Termination Criterion: fixed maximum number of iterations.

Additional notes on the testing can be found in the pdf report.
