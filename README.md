# address-splitter
A Java app for splitting address strings into street and house number properties

You can use via curl or similar, for example:
```
curl -X POST http://localhost:8080/address -H 'Content-Type: application/json' -d '{ "addressString": "405 big road" }'
```
which would return the response:
```
{"street":"big road","houseNumber":"405"}
```

It's a simple non-regex solution, whereby it first tries to identify the house number by looking for any numerical components of a provided address. Then, it checks if either or both of its neighbouring components are significant and could also make up part of the house number.
If there are no candidates found, it will assume there is no house number and return the whole string as the street. If there are more than one, it will compare them and choose the most appropriate based on known house number prefixes, its position in the address string (beginning or end is preferred), and what ratio of the base component's characters are numerical.
It will then remove the address components related to the highest scoring candidate house number, leaving the remainder of the address as the street.
