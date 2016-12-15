
def String date = '12/01/2014 00:00:00'
def Date d = Date.parse( 'dd/MM/yyyy HH:mm:ss', date )
println d.getTime();

def Date sys=new Date();
println sys.getTime();

println d.getTime()>sys.getTime()
println sys.getTime()>d.getTime()


println "Welcome to $language"
return "The End"
