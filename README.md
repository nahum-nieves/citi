# Citi Exercise
In order to run the application you need:
1. Maven 
2. JDK 1.8 (and JRE correctly configured on your path variables)
3. CLI (Command Line Interface) Linux preferred
4. git (optional)Firstly 

Let's get started.
 
Working on the repository directory:
1. Put your prompt into citi directory (cd citi)
2. run maven goal in order to build the application (mvn package) this command will execute the tests too
3. Run the application (java -jar  ./target/citi.jar or simpy  ./target/citi.jar)
4. You can check the methods on your web browser like this: http://localhost:8080/headers-filter?headerNames= for filtering headers method or http://localhost:8080/headers for non filtering headers method
5. Optional: If you're using your 8080 port you can define another free port by adding --server.port= arg like this:  ./target/citi.jar --server.port=8083
