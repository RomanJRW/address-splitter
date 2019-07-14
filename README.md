# address-splitter
A Java/Spring Boot web app for splitting address strings into street and house number properties.

It's a simple solution, whereby it first tries to identify the house number by first looking for any numerical components of a provided address. Then, it checks if either or both of its neighbouring components are significant and could also make up part of the house number, which are combined to make a house number candidate.
If there are no candidates found, it will assume there is no house number and return the whole string as the street. If there are more than one, it will compare them and choose the most appropriate based on whether it contains a known house number prefixe, the position of the candidate in the original address string (beginning or end is preferred), and what ratio of the candidate's base numerical component's characters are numbers.
It will then remove the address components related to the highest scoring candidate house number, leaving the remainder of the address as the street.

Common solutions to this or similar problems involve complex regex, which I find difficult to maintain and wanted to avoid. This solution only uses regex matching for splitting the original address string into separate components.

The test class contains a number of passing scenarios, which can easily be extended or modified. Alternatively you can actually run the app and test via curl or similar:

example request:
```
curl -X POST http://localhost:8080/address -H 'Content-Type: application/json' -d '{ "addressString": "405 example road" }'
```
example response:
```
{"street":"example road","housenumber":"405"}
```
