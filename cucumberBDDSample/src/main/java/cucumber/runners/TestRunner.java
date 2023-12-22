package cucumber.runners;


import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;



@RunWith(Cucumber.class) 
@Cucumber.Options(
features = "cucumber",
format = { "pretty",
"json:target/cucumber.json" }, 
tags = { "@regression" },
glue={"cucumber"}

		) 


public class TestRunner {

}

