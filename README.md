i Yehuda!

The main objective is to get to know our faastest framework and write a program that will help us understand whether a new version performance has gone better or worse.

V. 1. Clone our faastest framework (recall www.faastest.com we demonstrated) from:
 - https://github.com/nuweba/faasbenchmark
 - Follow the readme on the repo.
V. 2. Choose a specific test from our set of tests we perform (you can assist the terminal UI, tui, to pick a test). IncreasingCPULoadLvl1 may be suitable.
V. 3. Run a single run of the chosen test, using the framework, testing it on AWS only.
 - It will generate a "restults_*" directory.
 - We will address that directory as the input results (or- results from a former working version for our manner).
4. Write a program which will run another instance of that specific test you've picked, and compare it to our input

<A.a Create a program that run test (same test, different time)>
<B.b Compare both results of invocation-overhead output (min, max, average)>
Guidelines:
a. Generate a report about the performance (invocation overhead) of the test.
b. Add invocation overhead metrics of mean, max, min and spread to your report and compare them to the input results.
c. Chaos programming is very important (recover from any error or exception on the flow)
d. Write in any language of your choice (the faastest framework is written in go, but you can run in as a blackbox in your code).
e. You will need to use an AWS account for that exercise. We've already setup one for you. 
Your credentials are:
acces key id: AKIATM27G7R7VDZ3DCNY
secret access id: LeT+FY/kCi1pj6aS4kbHbr3kApXW24uSQE5YEks/
(you can run the faastest using AWS_ACCESS_KEY_ID=<access_key> AWS_SECRET_ACCESS_KEY=<secret>)

You might be required to install some dependencies on your machine in order to run our python, java and dotnet (it's best to run from Linux machine).

You will be measured by functionality, simplicity and robustness of your code

If something in the challenge is not clear enough or you have any questions, feel free to contact me.