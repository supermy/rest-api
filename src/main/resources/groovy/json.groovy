package groovy

/**
 * Created by moyong on 16/11/24.
 */
import groovy.json.*

class Me {
    String name
}

def o = new Me( name: 'jamesmo' )

println new JsonBuilder( o ).toPrettyString()
