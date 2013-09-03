import  groovy.json.JsonBuilder

def builder = new JsonBuilder()

builder {
	obj{
		name("andy") 
		age(32)
		address (
			['Shanghai','JiangXi'].collect{[city : it]}
	    )
    }
	obj1 {
		name( "grace")
		age (32)
		address( "Shanghai")
    }		
    obj2{
    	name("buhaiqing")
    	test({
    		address1 "nanjing"
    		address2 'xingang'
    		})
    }
}

println  builder.toPrettyString()

