package groovy

/**
 * Created by moyong on 16/11/28.
 */

import groovy.json.*

def jsonSlurper = new JsonSlurper();
def object = jsonSlurper.parseText(p2);

assert object instanceof Map;
assert object.name == 'John Doe';

println p1;
println p2;

return object.name;