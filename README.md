This is a sample Synapse Handler to encode request URLs. The configurations to engage in the handler in API-Manager-3.1.0 are as follows.



1. Add the following configurations in the <APIM_HOME>/repository/conf/deployment.toml file

enabled_global_handlers= ["custom_handler"]

[synapse_handlers]
custom_handler.name= "RequestEncodingHandler"
custom_handler.class= "org.wso2.handler.synapse.RequestEncodingHandler"

[synapse_properties]
escape_encode_chars = ",:"

Notes:
"enabled_global_handlers" property should be the first element of the deployment.toml file.

"custom_handler" properties contain name and the fully-qualified name of the Synapse handler class.

"escape_encode_chars" property should have the special characters that will be included in the request URLs.
",." above is an example value. This can be configured with any character/characters similarly. And the handler will encode the URI fragments that includes these character/characters.



2. To enable debug logs for the Synapse handler, add the following configurations in the <APIM_HOME>/repository/conf/log4j2.properties file.

logger.RequestEncodingHandler.name = org.wso2.handler.synapse.RequestEncodingHandler
logger.RequestEncodingHandler.level = DEBUG

Append the newly added logger name (RequestEncodingHandler) to "loggers" configuration which is a comma separated list of all active loggers.
ex: loggers = ....., JAGGERY_LOG, RequestEncodingHandler



3. Build the RequestEncodingHandler project using mvn clean install.



4. Once the sample is built copy the JAR file "org.wso2.handler.synapse-1.0-SNAPSHOT.jar"
to <APIM_HOME>/repository/components/lib and restart the server.



5. Once restarted the OSGI bundle will be placed in <APIM_HOME>/repository/components/dropins and
this also needs to be removed when the JAR file in the lib directory is replaced.
