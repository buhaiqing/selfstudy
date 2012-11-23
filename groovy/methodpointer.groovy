def list = ['A','B','C']
def addit = list.&add

addit 'D'

assert list == ['A','B','C', 'D']