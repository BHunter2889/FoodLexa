# AlexaSkillDemo
## The Api
We have created an API key specifically for this event. It will be disabled at the end of the weekend.

API Endpoint for Product Title Search: `https://dev-enterprise-sl-api.labelinsight.com/api/v4/20ff77e9-75af-410d-98ff-bef2febb0df7/data/search?title=<product title>`

API Key: `9qQUNqKiSo6HNuo1C41EBWD3RkLwjsN1PIKmbPKb`

Example curl:
```
curl -X GET \
     'https://dev-enterprise-sl-api.labelinsight.com/api/v4/20ff77e9-75af-410d-98ff-bef2febb0df7/data/search?title=cocoa' \
     -H 'cache-control: no-cache' \
     -H 'x-api-key: 9qQUNqKiSo6HNuo1C41EBWD3RkLwjsN1PIKmbPKb'
```

The search in the example above is for `title=cocoa` and it is backed by ElasticSearch.  So, all products with "cocoa" will be returned.
ElasticSearch tokenizes all strings on the ` ` character, so you get somewhat of a fuzzy search.
     

## Before you Begin
Ensure you have an Amazon Developer Account. You can sign in or create one here https://developer.amazon.com/

## Configure Your Alexa Skill Information
1. Login to your Amazon Developer Account
2. Navigate to the Alexa tab at the upper portion of the screen
3. Select Alexa Skills Kit Get Started >
4. Add a New Skill
5. Select Custom Interaction Model
6. Decide on a Name for your skill
7. Pick an invocation name (ie. Label Insight or Smart Label)

## Interaction Model
There is a sample intent schema located in the speech asset directory in this project, you can copy and paste the intent schema in the box. 
I have also included Sample Utterances for the defined Intents from above.

The Skill Builder Beta is a great way to define new intents and utterances. However, currently the beta does not support the slot type of AMAZON.LITERAL, utterances that use that slot type will have to be configured via JSON and sample utterances.

If you have opted into the beta and desire to go back to the non beta configuration. Via the dashboard tab at the bottom of the page there is a Leave Beta Preview button, you will not lose any configurations you have built and they will be ported to the non beta configuration.

Once you are happy with your Intents and your Utterances you can build the model which will bring you to the Configuration.

## How to Deploy
For this example the code supplied is set up to run in an AWS Lambda. You can create your lambda here: https://console.aws.amazon.com/console/home?region=us-east-1

### Creating the Lambda
1. Search Lambda on the main page of the AWS Console
2. Create Function
3. Author from scratch (If you prefer NodeJs or Python Amazon has some supplied examples for alexa skills)
4. Choose a name for your function hack-the-lou-alexa-skill
5. Create and Name a new role, you do not need to set any templates (hack-the-lou-alexa-skill-role)
6. Change the Runtime to Java
7. Change the handler to `com.labelinsight.UpcSpeechletRequestStreamHandler`
8. Run the following command to compile the code in this project `mvn assembly:assembly -DdescriptorId=jar-with-dependencies package`
9. The assembled jar file can be found in the target folder. `AlexaSkill-1.0.0-jar-with-dependencies.jar`
10. Upload the jar file and hit save.
11. Under the triggers tab, add a new trigger, select Alexa Skills Kit

Note: Under the Basic Setting in the configuration tab, it is a good idea to bump the memory to 512mb

### Link the Lambda to the Skill
1. At the top of the lambda function page there is an *ARN* copy that value
2. In the Alexa Skills Kit configuration select AWS Lambda ARN and paste the value copied from the Lambda function.
3. Leave the rest default and click next.

## Testing
1. On the test screen you are able to type in example utterances, you will see the generated request and the response from the lambda
2. If you have an alexa device registered to your Amazon account it also will automatically have the skill enabled. You can view it from the My Skills tab in the Alexa App
3. If you copy the generated request in the test screen from Alexa Skills Kit you can paste it to the Test event in Lambda to get quick tests of functionality as you update


