package groovy

/**
 * Created by moyong on 16/11/24.
 */
import groovy.json.JsonOutput;

def jsonLiteral = ["name": "james mo", "id" : 1]

println "JSON Literal as String : " + jsonLiteral
println "JSON Literal as JSON : " + JsonOutput.toJson(jsonLiteral)
println "JSON Literal as JSON formatted : "
println JsonOutput.prettyPrint(JsonOutput.toJson(jsonLiteral))
