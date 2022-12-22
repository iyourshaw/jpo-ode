# jpo-ode JSON Output Schemas

The jpo-ode supports receiving and decoding ASN1 messages from RSUs. The supported message types are currently BSM, MAP, SPaT, SRM and SSM. These are decoded into XML, deserialized into POJOs and finally serialized into JSON. This JSON output can be access from any of the corresponding message's JSON output Kafka topics:

- [topic.OdeBsmJson](schema-bsm.json)
- [topic.OdeMapJson](schema-map.json)
- [topic.OdeSpatJson](schema-spat.json)
- [topic.OdeSrmJson](schema-srm.json)
- [topic.OdeSsmJson](schema-ssm.json)

The output JSON of the ODE is complex but it is similar to the official standard of J2735 with some minor differences due to the form of their deserialized POJOs. To help implement proper data validation for the JSON output of the ODE into any data pipeline infrastructure, you may use the provided validation schemas within this directory.

## Testing the schemas
These schemas can be used for manual validation in the following steps:
1. To perform a simple schema validation check against output JSON data from the jpo-ode, you may use a schema validation site such as https://www.jsonschemavalidator.net. 
2. Retrieve a jpo-ode output message from the specific message type you would like to validate.
   - Example for retrieving output BSM with kafkacat: `kafkacat -b kafka-broker-ip:9092 -G my_consumer_group topic.OdeBsmJson -f '\nTopic: %t, Key: %k, Offset: %o, Timestamp: %T\nValue: %s\n'`
3. Copy and paste the provided schema into the left panel for the message type you plan to validate. 
4. Copy and paste the output JSON data from the jpo-ode to the right panel.
5. The site will describe any fields that may be incorrect or provide a green checkmark for a proper validation of the data.

## Next steps
These schemas can be used for automatic validation if used as a step in a data pipeline. 
- Many of the cloud services have message pipelines and provide different options to validate incoming data, including schema validation. 
- Confluent provides a service known as schema registry which is easy to configure as a part of a Kafka deployment to automatically validate messages as they are produced.
- Configuring KafkaStreams to validate jpo-ode output before processing the data further in a custom solution.