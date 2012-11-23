def list = ['A','B','C']
// Groovy method pointer
def addit = list.&add 

addit 'D'

assert list == ['A','B','C', 'D']